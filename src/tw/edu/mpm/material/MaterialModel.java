package tw.edu.mpm.material;

import tw.edu.mpm.math.Matrix3;
import tw.edu.mpm.math.Tensor3;
import tw.edu.mpm.particle.MaterialPoint;

/**
 * ============================================================
 * MPM3D-Java
 *
 * MaterialModel.java
 *
 * 材料模型介面 (Material Constitutive Model)
 *
 * 所有材料模型都必須實作本介面。
 *
 * 目前支援：
 *   • Linear Elastic
 *   • Mohr-Coulomb
 *   • Drucker-Prager
 *
 * 未來可擴充：
 *   • Modified Cam Clay
 *   • NorSand
 *   • Hypoplasticity
 *   • Viscoelastic
 *   • Viscoplastic
 *
 * StressUpdate 不需要知道材料種類，
 * 只需呼叫 updateStress() 即可。
 * ============================================================
 */
public interface MaterialModel {

	/**
	 * 材料名稱
	 */
	String getName();

	/**
	 * 材料密度 (kg/m^3)
	 */
	double getDensity();

	/**
	 * 楊氏係數 E (Pa)
	 */
	double getYoungsModulus();

	/**
	 * 浦松比 ν
	 */
	double getPoissonsRatio();

	/**
	 * 剪切模數
	 *
	 * G = E / [2(1+ν)]
	 */
	double getShearModulus();

	/**
	 * 體積模數
	 *
	 * K = E / [3(1-2ν)]
	 */
	double getBulkModulus();

	/**
	 * Lamé 第一常數 λ
	 */
	double getLameLambda();

	/**
	 * Lamé 第二常數 μ (=G)
	 */
	double getLameMu();

	/**
	 * 更新材料應力
	 *
	 * σ(n+1)=f(σ(n),Δε)
	 *
	 * @param particle 材料點
	 * @param stress 舊應力
	 * @param strainIncrement 應變增量
	 * @param dt 時間增量
	 *
	 * @return 更新後應力
	 */
	Tensor3 updateStress(MaterialPoint particle, Tensor3 stress, Matrix3 strainIncrement, double dt);

	/**
	 * 是否為彈塑性材料
	 */
	boolean isPlastic();

	/**
	 * 是否發生降伏
	 *
	 * 純彈性材料固定回傳 false
	 */
	boolean hasYielded(Tensor3 stress);

	/**
	 * 重設材料內部狀態
	 *
	 * 例如：
	 *   Plastic Strain
	 *   Hardening Variable
	 *   Void Ratio
	 */
	void resetState(MaterialPoint particle);

	/**
	 * 材料穩定時間步長估計
	 *
	 * CFL：
	 *
	 * dt <= dx / c
	 *
	 * c = sqrt((K+4G/3)/ρ)
	 *
	 * @param cellSize 網格尺寸
	 * @return 建議最大時間步長
	 */
	double computeStableTimeStep(double cellSize);

	/**
	 * P 波速度
	 */
	double computeWaveSpeed();

}