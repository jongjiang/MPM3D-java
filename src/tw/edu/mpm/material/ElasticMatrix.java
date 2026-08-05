package tw.edu.mpm.material;

import tw.edu.mpm.math.Matrix3;
import tw.edu.mpm.math.Tensor3;

/**
 * ============================================================
 * MPM3D-Java
 *
 * ElasticMatrix.java
 *
 * 三維各向同性彈性矩陣
 *
 * 本類別不建立傳統 FEM 的 6×6 D Matrix，
 * 而是直接利用 Hooke Law：
 *
 *      Δσ = λ tr(Δε) I + 2μ Δε
 *
 * 計算應力增量。
 *
 * 適用：
 *
 *  • Linear Elastic
 *  • Mohr-Coulomb (Elastic Predictor)
 *  • Drucker-Prager (Elastic Predictor)
 *  • Cam-Clay
 *
 * ============================================================
 */
public class ElasticMatrix {

	/**
	 * Lamé 第一常數
	 */
	private final double lambda;

	/**
	 * Lamé 第二常數 (Shear Modulus)
	 */
	private final double mu;

	/**
	 * 建構子
	 *
	 * @param youngsModulus 楊氏係數 E
	 * @param poissonsRatio 浦松比 ν
	 */
	public ElasticMatrix(double youngsModulus, double poissonsRatio) {

		this.lambda = youngsModulus * poissonsRatio / ((1.0 + poissonsRatio) * (1.0 - 2.0 * poissonsRatio));

		this.mu = youngsModulus / (2.0 * (1.0 + poissonsRatio));
	}

	/**
	 * 建構子
	 *
	 * 直接指定 Lamé 常數
	 */
	public ElasticMatrix(double lambda, double mu, boolean direct) {

		this.lambda = lambda;
		this.mu = mu;
	}

	/**
	 * 計算應力增量
	 *
	 * Δσ = λ tr(Δε) I + 2μ Δε
	 *
	 * @param strainIncrement 應變增量
	 * @return 應力增量
	 */
	public Tensor3 computeStressIncrement(Matrix3 strainIncrement) {
		Tensor3 deltaStress = Tensor3.fromMatrix(strainIncrement);
		deltaStress = deltaStress.multiply(2.0 * mu);
		deltaStress.addDiagonal(lambda * strainIncrement.trace());
		return deltaStress;
	}

	/**
	 * 更新應力
	 *
	 * σ(n+1)=σ(n)+Δσ
	 */
	public Tensor3 updateStress(Tensor3 oldStress, Matrix3 strainIncrement) {

		return oldStress.add(computeStressIncrement(strainIncrement));
	}

	/**
	 * 剪切模數
	 */
	public double getShearModulus() {
		return mu;
	}

	/**
	 * Lamé 第一常數
	 */
	public double getLambda() {
		return lambda;
	}

	/**
	 * 體積模數
	 */
	public double getBulkModulus() {

		return lambda + 2.0 * mu / 3.0;
	}

	/**
	 * P-Wave 波速
	 *
	 * @param density 材料密度
	 */
	public double computeWaveSpeed(double density) {

		return Math.sqrt((getBulkModulus() + 4.0 * mu / 3.0) / density);
	}

	@Override
	public String toString() {

		return String.format("ElasticMatrix[λ=%.6e, μ=%.6e, K=%.6e]", lambda, mu, getBulkModulus());
	}

}