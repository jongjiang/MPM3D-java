package tw.edu.mpm.material;

import java.util.Map;

/**
 * ============================================================
 * MPM3D-Java
 *
 * MaterialFactory.java
 *
 * 材料工廠
 *
 * 功能：
 *   1. 根據 MaterialType 建立材料
 *   2. 統一材料建立流程
 *   3. 避免程式直接 new 材料類別
 *
 * ============================================================
 */
public final class MaterialFactory {

	/**
	 * 工具類別，不允許建立實例
	 */
	private MaterialFactory() {
	}

	/**
	 * 建立材料
	 *
	 * @param type 材料種類
	 * @param parameters 材料參數
	 * @return MaterialModel
	 */
	public static MaterialModel create(MaterialType type, Map<String, Double> parameters) {

		if (type == null) {
			throw new IllegalArgumentException("MaterialType 不可為 null");
		}

		if (parameters == null) {
			throw new IllegalArgumentException("Material parameters 不可為 null");
		}

		switch (type) {

		case LINEAR_ELASTIC:
			return createLinearElastic(parameters);

		case MOHR_COULOMB:
			return createMohrCoulomb(parameters);

		case DRUCKER_PRAGER:
			return createDruckerPrager(parameters);

		default:
			throw new UnsupportedOperationException("尚未支援材料：" + type);
		}
	}

	/**
	 * 建立線彈性材料
	 */
	private static MaterialModel createLinearElastic(Map<String, Double> p) {

		return new LinearElastic(get(p, "density"), get(p, "youngsModulus"), get(p, "poissonsRatio"));
	}

	/**
	 * 建立 Mohr-Coulomb 材料
	 */
	private static MaterialModel createMohrCoulomb(Map<String, Double> p) {

		return new MohrCoulomb(get(p, "density"), get(p, "youngsModulus"), get(p, "poissonsRatio"), get(p, "cohesion"), get(p, "frictionAngle"), get(p, "dilationAngle"));
	}

	/**
	 * 建立 Drucker-Prager 材料
	 */
	private static MaterialModel createDruckerPrager(Map<String, Double> p) {

		return new DruckerPrager(get(p, "density"), get(p, "youngsModulus"), get(p, "poissonsRatio"), get(p, "cohesion"), get(p, "frictionAngle"), get(p, "dilationAngle"));
	}

	/**
	 * 取得材料參數
	 */
	private static double get(Map<String, Double> parameters, String key) {

		Double value = parameters.get(key);

		if (value == null) {
			throw new IllegalArgumentException("缺少材料參數：" + key);
		}

		return value;
	}

}