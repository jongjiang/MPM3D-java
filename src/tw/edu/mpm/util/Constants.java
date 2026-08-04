package tw.edu.mpm.util;

/**
 * ============================================================
 * MPM3D-Java
 *
 * Constants.java
 *
 * 功能：
 *     儲存 Material Point Method (MPM)
 *     模擬所需的全域常數
 *
 * ============================================================
 *
 * MPM基本概念：
 *
 *     MPM = Particle + Background Grid
 *
 *
 * Particle:
 *     儲存材料狀態
 *
 *     - 位置
 *     - 速度
 *     - 質量
 *     - 密度
 *     - 應力
 *     - 應變
 *
 *
 * Grid:
 *     負責計算
 *
 *     - 動量傳遞
 *     - 內力
 *     - 外力
 *
 *
 * ============================================================
 */

public final class Constants {

	/*
	 * ========================================================
	 * 防止建立物件
	 *
	 * Constants只提供static變數
	 *
	 * ========================================================
	 */

	private Constants() {

	}

	/*
	 * ========================================================
	 * 空間維度
	 *
	 * MPM3D：
	 *
	 * X,Y,Z 三方向
	 *
	 * ========================================================
	 */

	public static final int DIMENSION = 3;

	/*
	 * ========================================================
	 * 數值計算設定
	 * ========================================================
	 */

	/*
	 * 初始時間
	 */

	public static final double INITIAL_TIME = 0.0;

	/*
	 * 總分析時間
	 *
	 * 單位：
	 * second
	 */

	public static final double TOTAL_TIME = 1.0;

	/*
	 * 時間步長
	 *
	 * Explicit MPM使用中央差分法
	 *
	 * dt必須符合：
	 *
	 * dt < dx / wave_speed
	 *
	 */

	public static final double TIME_STEP = 1.0e-4;

	/*
	 * CFL安全係數
	 *
	 * 用於自動時間步長控制
	 *
	 */

	public static final double CFL_FACTOR = 0.4;

	/*
	 * ========================================================
	 * 重力
	 * ========================================================
	 */

	/*
	 * 地球重力加速度
	 *
	 * m/s^2
	 */

	public static final double GRAVITY = -9.81;

	/*
	 * ========================================================
	 * Grid設定
	 * ========================================================
	 */

	/*
	 * Grid Cell尺寸
	 *
	 * meter
	 */

	public static final double GRID_SIZE = 0.1;

	/*
	 * X方向Grid數量
	 */

	public static final int GRID_NX = 50;

	/*
	 * Y方向Grid數量
	 */

	public static final int GRID_NY = 50;

	/*
	 * Z方向Grid數量
	 */

	public static final int GRID_NZ = 50;

	/*
	 * ========================================================
	 * Material Point設定
	 * ========================================================
	 */

	/*
	 * 每個cell內Particle數量
	 *
	 * 常見：
	 *
	 * 2x2x2 = 8 particles
	 *
	 */

	public static final int PARTICLES_PER_CELL = 8;

	/*
	 * 最大Particle數量
	 */

	public static final int MAX_PARTICLES = 100000;

	/*
	 * ========================================================
	 * 材料參數
	 *
	 * Linear Elastic Model
	 *
	 * ========================================================
	 */

	/*
	 * Young's Modulus
	 *
	 * 單位：
	 *
	 * Pa
	 *
	 */

	public static final double YOUNG_MODULUS = 1.0e7;

	/*
	 * Poisson Ratio
	 *
	 * 範圍：
	 *
	 * 0 < ν < 0.5
	 *
	 */

	public static final double POISSON_RATIO = 0.30;

	/*
	 * Density
	 *
	 * kg/m^3
	 *
	 * 土壤約：
	 *
	 * 1800~2200
	 *
	 */

	public static final double DENSITY = 2000.0;

	/*
	 * ========================================================
	 * Elastic常數
	 *
	 * Lamé Parameters
	 *
	 * λ, μ
	 *
	 * ========================================================
	 */

	/*
	 * Shear Modulus
	 *
	 * μ = E / 2(1+ν)
	 *
	 */

	public static final double SHEAR_MODULUS = YOUNG_MODULUS / (2.0 * (1.0 + POISSON_RATIO));

	/*
	 * Lamé first parameter
	 *
	 * λ =
	 *
	 * Eν /
	 *
	 * (1+ν)(1-2ν)
	 *
	 */

	public static final double LAME_LAMBDA = (YOUNG_MODULUS * POISSON_RATIO) / ((1.0 + POISSON_RATIO) * (1.0 - 2.0 * POISSON_RATIO));

	/*
	 * ========================================================
	 * 邊界條件設定
	 * ========================================================
	 */

	/*
	 * 固定邊界Node
	 */

	public static final boolean FIX_BOUNDARY = true;

	/*
	 * ========================================================
	 * 輸出設定
	 * ========================================================
	 */

	/*
	 * VTK輸出間隔
	 *
	 * 每多少step輸出一次
	 *
	 */

	public static final int OUTPUT_INTERVAL = 100;

	/*
	 * 輸出資料夾
	 */

	public static final String OUTPUT_FOLDER = "output/";

}