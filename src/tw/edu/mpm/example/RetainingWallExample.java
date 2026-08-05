package tw.edu.mpm.example;

import tw.edu.mpm.math.Vector3;

import tw.edu.mpm.mesh.Grid;

import tw.edu.mpm.particle.ParticleGenerator;
import tw.edu.mpm.particle.ParticleSet;

import tw.edu.mpm.solver.MPMSolver;
import tw.edu.mpm.solver.StressUpdate;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * RetainingWallExample.java
 *
 * 擋土牆背填土分析案例
 *
 * 模型：
 *     Soil + Retaining Wall
 *
 * 分析：
 *     Gravity loading
 *     Soil deformation
 *     
 * ============================================================
 */

public class RetainingWallExample {

	public static void main(String[] args) {

		System.out.println("MPM Retaining Wall Example");

		/*
		 * ====================================================
		 *
		 * 1. 建立 Background Grid
		 *
		 * 範圍:
		 * x = 0 ~ 10 m
		 * y = 0 ~  8 m
		 * z = 0 ~  2 m
		 *
		 * ====================================================
		 */

		Grid grid = new Grid(40, 32, 8, 0.25);

		/*
		 * ====================================================
		 *
		 * 2. 建立背填土 Material Points
		 *
		 *            Wall
		 *
		 *              |
		 *
		 *  Soil        |
		 *
		 * ███████      |
		 * ███████      |
		 * ███████      |
		 *
		 * ====================================================
		 */

		ParticleSet soil = ParticleGenerator.createBlock(new Vector3(0.5, 0.25, 0.25), 25, 20, 5, 0.2, 1800.0);

		/*
		 * ====================================================
		 * 3. 建立 MPM Solver
		 * ====================================================
		 */

		MPMSolver solver = new MPMSolver(grid, soil);

		/*
		 * ====================================================
		 * 4. 土壤材料參數
		 *
		 * E 楊氏係數（Young's Modulus）
		 * ν 浦松比（Poisson's Ratio）
		 * ====================================================
		 */

		StressUpdate soilMaterial = new StressUpdate(1.0e7, 0.30);
		solver.setStressUpdate(soilMaterial);

		/*
		 * ====================================================
		 * 5. Time Control
		 * ====================================================
		 */

		solver.setTimeStep(0.0005);
		solver.setEndTime(3.0);

		/*
		 * ====================================================
		 * 6. Run Analysis
		 * ====================================================
		 */

		solver.run();

		/*
		 * ====================================================
		 * 7. Output displacement
		 * ====================================================
		 */

		System.out.println("Final Soil Particle Position");
		System.out.println(soil.get(0).getPosition());
		System.out.println("Simulation Complete");

	}

}