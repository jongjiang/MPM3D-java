package tw.edu.mpm.material;

import tw.edu.mpm.math.Tensor3;

/**
 * ============================================================
 * MPM3D-Java
 *
 * PlasticIntegrator.java
 *
 * 塑性積分器 (Plastic Integrator)
 *
 * 功能：
 *   1. 接收 Trial Stress
 *   2. 判斷是否降伏
 *   3. 執行 Return Mapping
 *
 * 目前版本：
 *   - 提供統一介面
 *   - 保留 Return Mapping 擴充點
 *
 * 後續可加入：
 *   - Newton-Raphson Return Mapping
 *   - Closest Point Projection
 *   - Cutting Plane Algorithm
 *
 * ============================================================
 */
public final class PlasticIntegrator {

	/**
	 * 工具類別，不允許建立實例
	 */
	private PlasticIntegrator() {
	}

	/**
	 * 對 Trial Stress 執行塑性修正。
	 *
	 * @param material    材料模型
	 * @param trialStress Trial Stress
	 * @return 修正後應力
	 */
	public static Tensor3 integrate(MaterialModel material, Tensor3 trialStress) {

		if (!material.isPlastic()) {
			return trialStress;
		}

		if (!material.hasYielded(trialStress)) {
			return trialStress;
		}

		if (material instanceof MohrCoulomb) {
			return integrateMohrCoulomb((MohrCoulomb) material, trialStress);
		}

		if (material instanceof DruckerPrager) {
			return integrateDruckerPrager((DruckerPrager) material, trialStress);
		}

		throw new UnsupportedOperationException("Unsupported plastic material : " + material.getName());
	}

	/**
	 * Mohr-Coulomb Return Mapping
	 *
	 * 目前保留介面，後續加入 Newton-Raphson 演算法。
	 */
	private static Tensor3 integrateMohrCoulomb(MohrCoulomb material, Tensor3 trialStress) {

		// TODO:
		// Newton-Raphson Return Mapping
		// Plastic Multiplier Δλ
		// Consistent Tangent Operator

		return new Tensor3(trialStress);
	}

	/**
	 * Drucker-Prager Return Mapping
	 *
	 * 目前保留介面，後續加入 Closest Point Projection。
	 */
	private static Tensor3 integrateDruckerPrager(DruckerPrager material, Tensor3 trialStress) {

		// TODO:
		// Return Mapping
		// Plastic Flow
		// Hardening

		return new Tensor3(trialStress);
	}

}