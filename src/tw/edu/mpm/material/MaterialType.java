package tw.edu.mpm.material;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * MaterialType.java
 *
 * 材料種類列舉
 *
 *
 * 用於：
 *
 *     MaterialFactory
 *
 *     MPMInput
 *
 *     案例設定檔
 *
 *
 * 支援材料：
 *
 *     LINEAR_ELASTIC
 *
 *     MOHR_COULOMB
 *
 *     DRUCKER_PRAGER
 *
 *
 * 未來可擴充：
 *
 *     CAM_CLAY
 *
 *     NOR_SAND
 *
 *     HYPOPLASTIC
 *
 *
 * ============================================================
 */
public enum MaterialType {

	/**
	 * 線彈性材料
	 *
	 * Hooke Law
	 */
	LINEAR_ELASTIC,

	/**
	 * Mohr-Coulomb 彈塑性材料
	 *
	 * 土壤常用模型
	 */
	MOHR_COULOMB,

	/**
	 * Drucker-Prager 彈塑性材料
	 *
	 * Mohr-Coulomb 平滑近似
	 */
	DRUCKER_PRAGER;

	/**
	 * ========================================================
	 *
	 * 字串轉換 MaterialType
	 *
	 * 支援：
	 *
	 * "linear_elastic"
	 *
	 * "LinearElastic"
	 *
	 * "LINEAR_ELASTIC"
	 *
	 * ========================================================
	 */
	public static MaterialType fromString(String value) {

		if (value == null) {
			throw new IllegalArgumentException("Material type cannot be null");
		}

		String name = value.trim().toUpperCase().replace("-", "_").replace(" ", "_");

		switch (name) {
			case "LINEAR_ELASTIC":
			case "LINEARELASTIC":
			case "ELASTIC":
				return LINEAR_ELASTIC;
	
			case "MOHR_COULOMB":
			case "MOHRCOULOMB":
			case "MC":
				return MOHR_COULOMB;
	
			case "DRUCKER_PRAGER":
			case "DRUCKERPRAGER":
			case "DP":
				return DRUCKER_PRAGER;
	
			default:
				throw new IllegalArgumentException("Unknown material type : " + value);
		}

	}

	/**
	 * ========================================================
	 *
	 * 是否為塑性材料
	 *
	 * ========================================================
	 */
	public boolean isPlastic() {

		switch (this) {
			case MOHR_COULOMB:
			case DRUCKER_PRAGER:
				return true;
	
			case LINEAR_ELASTIC:
			default:
				return false;
		}

	}

	/**
	 * ========================================================
	 *
	 * 中文名稱
	 *
	 * ========================================================
	 */
	public String chineseName() {

		switch (this) {
			case LINEAR_ELASTIC:
				return "線彈性材料";
	
			case MOHR_COULOMB:
				return "Mohr-Coulomb土壤模型";
	
			case DRUCKER_PRAGER:
				return "Drucker-Prager土壤模型";
	
			default:
				return "未知材料";
		}

	}

}