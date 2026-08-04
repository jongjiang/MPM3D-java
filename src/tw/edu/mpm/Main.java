package tw.edu.mpm;

/**
 * ============================================================
 * MPM3D-Java
 *
 * Material Point Method 三維數值分析程式
 *
 * Main.java
 *
 * 功能：
 *     1. 程式入口
 *     2. 建立 MPM 模擬環境
 *     3. 控制時間積分流程
 *
 * 後續將加入：
 *     - Background Grid
 *     - Material Point
 *     - Constitutive Model
 *     - Boundary Condition
 *     - Contact Algorithm
 *     - VTK Output
 *
 * ============================================================
 */

public class Main {

	/**
	 * ========================================================
	 * Java 主程式入口
	 *
	 * @param args
	 *      command line arguments
	 *
	 * ========================================================
	 */

	public static void main(String[] args) {

		/*
		 * ----------------------------------------------------
		 * 顯示程式資訊
		 * ----------------------------------------------------
		 */
		System.out.println("====================================");
		System.out.println("      MPM3D-Java Solver");
		System.out.println(" Material Point Method Simulator");
		System.out.println("====================================");

		/*
		 * ----------------------------------------------------
		 *
		 * 1. 建立模擬參數
		 *
		 * 後續會改為：
		 *
		 * SimulationConfig.java
		 *
		 * ----------------------------------------------------
		 */

		double totalTime = 1.0;

		/*
		 * 時間步長
		 *
		 * MPM通常採用Explicit Time Integration
		 *
		 * Δt 必須滿足 CFL stability condition
		 *
		 */

		double dt = 0.001;

		/*
		 * 計算總步數
		 */

		int numberOfSteps = (int) (totalTime / dt);

		/*
		 * ----------------------------------------------------
		 *
		 * 2. 建立 MPM Solver
		 *
		 * 目前先使用空框架
		 *
		 * 後續會替換成：
		 *
		 * ExplicitMPMSolver solver
		 *
		 * ----------------------------------------------------
		 */

		System.out.println("初始化 MPM Solver...");

		/*
		 * ----------------------------------------------------
		 *
		 * 3. 建立測試物質點
		 *
		 * Material Point 是 MPM 核心資料結構
		 *
		 * 每個 particle 儲存：
		 *
		 * 位置 x
		 * 速度 v
		 * 質量 m
		 * 密度 rho
		 * 應力 sigma
		 * 應變 epsilon
		 *
		 *
		 * 下一章會建立：
		 *
		 * particle.MaterialPoint
		 *
		 * ----------------------------------------------------
		 */

		System.out.println("建立 Material Points...");

		int particleCount = 1000;

		System.out.println("Particle 數量 = " + particleCount);

		/*
		 * ----------------------------------------------------
		 *
		 * 4. MPM時間積分迴圈
		 *
		 * 基本流程：
		 *
		 *
		 *     Start Time Step
		 *
		 *          |
		 *
		 *          V
		 *
		 *     Particle → Grid
		 *
		 *          |
		 *
		 *          V
		 *
		 *     Solve Grid Momentum
		 *
		 *          |
		 *
		 *          V
		 *
		 *     Grid → Particle
		 *
		 *          |
		 *
		 *          V
		 *
		 *     Update Stress
		 *
		 *
		 * ----------------------------------------------------
		 */

		for (int step = 0; step < numberOfSteps; step++) {

			double currentTime = step * dt;

			/*
			 * 顯示計算進度
			 */

			if (step % 100 == 0) {
				System.out.println("Time step = " + step + "  Time = " + currentTime);
			}

			/*
			 * ------------------------------------------------
			 *
			 * 以下為未來 MPM 核心流程
			 *
			 * ------------------------------------------------
			 */

			// 1. Particle to Grid
			// solver.P2G();

			// 2. 計算 Grid Force
			// solver.computeGridForce();

			// 3. 更新 Grid Velocity
			// solver.updateGridVelocity();

			// 4. Grid to Particle
			// solver.G2P();

			// 5. 更新應力
			// solver.updateStress();

		}

		/*
		 * ----------------------------------------------------
		 *
		 * 模擬完成
		 *
		 * ----------------------------------------------------
		 */

		System.out.println("====================================");

		System.out.println("MPM Simulation Finished");

		System.out.println("====================================");

	}

}