package tw.edu.mpm.material;

import tw.edu.mpm.math.Matrix3;
import tw.edu.mpm.math.Tensor3;
import tw.edu.mpm.particle.MaterialPoint;

/**
 * ============================================================
 * MPM3D-Java
 *
 * AbstractMaterial.java
 *
 * 材料模型抽象基底類別
 *
 * 所有材料模型共用：
 *  • 材料基本參數
 *  • Lamé 常數
 *  • 波速
 *  • CFL 時間步長
 *  • 降伏預設行為
 *
 * 子類別只需實作：
 *  • updateStress()
 *
 * ============================================================
 */
public abstract class AbstractMaterial implements MaterialModel {

	/**
	 * 材料名稱
	 */
	protected String name;

	/**
	 * 密度 (kg/m³)
	 */
	protected double density;

	/**
	 * 楊氏係數 E (Pa)
	 */
	protected double youngsModulus;

	/**
	 * 浦松比 ν
	 */
	protected double poissonsRatio;

	/**
	 * 建構子
	 */
	protected AbstractMaterial(String name, double density, double youngsModulus, double poissonsRatio) {

		this.name = name;
		this.density = density;
		this.youngsModulus = youngsModulus;
		this.poissonsRatio = poissonsRatio;
	}

	// --------------------------------------------------------
	// 基本材料參數
	// --------------------------------------------------------

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getDensity() {
		return density;
	}

	@Override
	public double getYoungsModulus() {
		return youngsModulus;
	}

	@Override
	public double getPoissonsRatio() {
		return poissonsRatio;
	}

	/**
	 * 剪切模數
	 *
	 * G = E / [2(1+ν)]
	 */
	@Override
	public double getShearModulus() {

		return youngsModulus / (2.0 * (1.0 + poissonsRatio));
	}

	/**
	 * 體積模數
	 *
	 * K = E / [3(1-2ν)]
	 */
	@Override
	public double getBulkModulus() {

		return youngsModulus / (3.0 * (1.0 - 2.0 * poissonsRatio));
	}

	/**
	 * Lamé 第一常數 λ
	 */
	@Override
	public double getLameLambda() {

		return youngsModulus * poissonsRatio / ((1.0 + poissonsRatio) * (1.0 - 2.0 * poissonsRatio));
	}

	/**
	 * Lamé 第二常數 μ
	 */
	@Override
	public double getLameMu() {

		return getShearModulus();
	}

	// --------------------------------------------------------
	// 波速
	// --------------------------------------------------------

	/**
	 * P-wave 波速
	 */
	@Override
	public double computeWaveSpeed() {

		double k = getBulkModulus();
		double g = getShearModulus();

		return Math.sqrt((k + 4.0 * g / 3.0) / density);
	}

	/**
	 * CFL 穩定時間步長
	 */
	@Override
	public double computeStableTimeStep(double cellSize) {

		return cellSize / computeWaveSpeed();
	}

	// --------------------------------------------------------
	// 預設行為
	// --------------------------------------------------------

	/**
	 * 預設：彈性材料
	 */
	@Override
	public boolean isPlastic() {

		return false;
	}

	/**
	 * 預設：不降伏
	 */
	@Override
	public boolean hasYielded(Tensor3 stress) {

		return false;
	}

	/**
	 * 預設：無內部狀態
	 */
	@Override
	public void resetState(MaterialPoint particle) {

		// 線彈性材料不需要任何內部狀態
	}

	// --------------------------------------------------------
	// 子類別必須實作
	// --------------------------------------------------------

	/**
	 * 更新應力
	 *
	 * σ(n+1)=f(σ(n),Δε)
	 */
	@Override
	public abstract Tensor3 updateStress(MaterialPoint particle, Tensor3 stress, Matrix3 strainIncrement, double dt);

	// --------------------------------------------------------
	// Setter
	// --------------------------------------------------------

	public void setDensity(double density) {
		this.density = density;
	}

	public void setYoungsModulus(double youngsModulus) {
		this.youngsModulus = youngsModulus;
	}

	public void setPoissonsRatio(double poissonsRatio) {
		this.poissonsRatio = poissonsRatio;
	}

	// --------------------------------------------------------
	// Debug
	// --------------------------------------------------------

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append("Material : ").append(name).append('\n');
		sb.append("Density  : ").append(density).append(" kg/m^3\n");
		sb.append("E        : ").append(youngsModulus).append(" Pa\n");
		sb.append("nu       : ").append(poissonsRatio).append('\n');
		sb.append("G        : ").append(getShearModulus()).append(" Pa\n");
		sb.append("K        : ").append(getBulkModulus()).append(" Pa\n");
		sb.append("Lambda   : ").append(getLameLambda()).append(" Pa\n");
		sb.append("Mu       : ").append(getLameMu()).append(" Pa\n");
		sb.append("WaveSpeed: ").append(computeWaveSpeed()).append(" m/s");

		return sb.toString();
	}

}