package tw.edu.mpm.example;

import tw.edu.mpm.math.Vector3;

import tw.edu.mpm.mesh.Grid;

import tw.edu.mpm.particle.ParticleGenerator;
import tw.edu.mpm.particle.ParticleSet;

import tw.edu.mpm.solver.MPMSolver;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * FreeFallExample.java
 * 自由落體驗證案例
 *
 * 目的：
 *
 *     1. 測試Gravity
 *     2. 測試P2G/G2P
 *     3. 測試Time Integration
 *
 * ============================================================
 */

public class FreeFallExample {

	public static void main(String[] args) {

		System.out.println("MPM Free Fall Test");

		/*
		 * 1. 建立Grid
		 *
		 * 範圍：
		 * x:0~10
		 * y:0~10
		 * z:0~10
		 */
		Grid grid =	new Grid(20, 20, 20, 0.5);

		/*
		 * 2. 建立Particle Block
		 *
		 * 高度5m
		 */
		ParticleSet particles =	ParticleGenerator.createBlock(new Vector3(4, 5, 4), 5, 5, 5, 0.2, 1800);

		/*
		 * 3. 建立Solver
		 */
		MPMSolver solver = new MPMSolver(grid, particles);

		/*
		 * 時間設定
		 */
		solver.setTimeStep(0.0005);
		solver.setEndTime(1.2);

		/*
		 * Run
		 */
		solver.run();
		System.out.println("Particle final position");
		System.out.println(particles.get(0).getPosition());

	}

}