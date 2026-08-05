package tw.edu.mpm.solver;

import tw.edu.mpm.mesh.Cell;
import tw.edu.mpm.mesh.Grid;
import tw.edu.mpm.mesh.GridNode;

import tw.edu.mpm.math.Matrix3;
import tw.edu.mpm.math.Tensor3;
import tw.edu.mpm.math.Vector3;

import tw.edu.mpm.particle.MaterialPoint;
import tw.edu.mpm.particle.ParticleSet;

import tw.edu.mpm.util.MathUtil;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * StressUpdate.java
 *
 *
 * Constitutive Model
 *
 *
 * Linear Elastic Material
 *
 *
 * 功能：
 *
 *     1. 計算 velocity gradient
 *     2. 計算 strain increment
 *     3. 更新 stress
 *
 * ============================================================
 */

public class StressUpdate {

	/*
	 * Young modulus
	 */

	private double young;

	/*
	 * Poisson ratio
	 */

	private double poisson;

	/*
	 * Lamé parameter
	 */

	private double lambda;

	private double mu;

	/**
	 *
	 * Constructor
	 *
	 * @param young   楊氏係數（Young's Modulus）
	 * @param poisson 浦松比（Poisson's Ratio）
	 */
	public StressUpdate(double young, double poisson) {

		this.young = young;

		this.poisson = poisson;

		lambda = MathUtil.lameLambda(young, poisson);

		mu = MathUtil.shearModulus(young, poisson);

	}

	/**
	 *
	 * Update Stress
	 *
	 * σ(n+1) = σ(n) + Δσ
	 *
	 */

	public void update(ParticleSet particles, Grid grid, double dt) {

		
		int particleCount = particles.size();
		for (int i = 0; i < particleCount; i++) {
			MaterialPoint p = particles.get(i);	

			Cell cell =	grid.findCell(p.getPosition());

			if (cell == null) {
				continue;
			}

			/*
			 * 計算 Velocity Gradient
			 * L=∇v
			 */

			Matrix3 velocityGradient = computeVelocityGradient(p, cell);

			/*
			 * Strain rate
			 * D=0.5(L+LT)
			 */

			Tensor3 strainRate = computeStrainRate(velocityGradient);

			/*
			 * strain increment
			 * Δε=DΔt
			 */

			Tensor3 strainIncrement = strainRate.multiply(dt);

			/*
			 * Stress increment
			 * Δσ
			 */
			elasticStress(strainIncrement, p.getStress());
			
			p.setStrain(p.getStrain().add(strainIncrement));
			
		}

	}

	/**
	 *
	 * Velocity Gradient
	 *
	 *
	 * L = Σ vi ⊗ ∇Ni
	 *
	 */

	private Matrix3 computeVelocityGradient(MaterialPoint p, Cell cell) {

		Matrix3 L = new Matrix3();

//		double[] N = cell.shapeFunction(p.getPosition());

		Vector3[] grad = cell.shapeGradient(p.getPosition());

		GridNode[] nodes = cell.getNodes();

		for (int i = 0; i < 8; i++) {

			Vector3 v = nodes[i].getVelocity();
			Vector3 g = grad[i];
			L.addOuterProduct(v, g);

		}

		return L;

	}

	/**
	 *
	 * Strain Rate
	 *
	 *
	 * D=0.5(L+LT)
	 *
	 */

	private Tensor3 computeStrainRate(Matrix3 L) {

		Matrix3 LT = L.transpose();

		Matrix3 D =	L.add(LT).multiply(0.5);

		return Tensor3.fromMatrix(D);

	}

	/**
	 * ============================================================
	 * 線彈性應力更新 (In-place) 優化後
	 *
	 * stress += λ tr(Δε) I + 2μ Δε
	 *
	 * 不建立任何 Tensor3 物件
	 * ============================================================
	 */
	private void elasticStress(Tensor3 strainIncrement, Tensor3 stress) {

	    final double trace = strainIncrement.get(0, 0) + strainIncrement.get(1, 1) + strainIncrement.get(2, 2);

	    final double volumetric = lambda * trace;

	    // σxx
	    stress.set(
	            0,
	            0,
	            stress.get(0,0)
	            + volumetric
	            + 2.0 * mu * strainIncrement.get(0,0));

	    // σyy
	    stress.set(
	            1,
	            1,
	            stress.get(1,1)
	            + volumetric
	            + 2.0 * mu * strainIncrement.get(1,1));

	    // σzz
	    stress.set(
	            2,
	            2,
	            stress.get(2,2)
	            + volumetric
	            + 2.0 * mu * strainIncrement.get(2,2));

	    // σxy
	    stress.set(
	            0,
	            1,
	            stress.get(0,1)
	            + 2.0 * mu * strainIncrement.get(0,1));

	    // σyx
	    stress.set(
	            1,
	            0,
	            stress.get(1,0)
	            + 2.0 * mu * strainIncrement.get(1,0));

	    // σxz
	    stress.set(
	            0,
	            2,
	            stress.get(0,2)
	            + 2.0 * mu * strainIncrement.get(0,2));

	    // σzx
	    stress.set(
	            2,
	            0,
	            stress.get(2,0)
	            + 2.0 * mu * strainIncrement.get(2,0));

	    // σyz
	    stress.set(
	            1,
	            2,
	            stress.get(1,2)
	            + 2.0 * mu * strainIncrement.get(1,2));

	    // σzy
	    stress.set(
	            2,
	            1,
	            stress.get(2,1)
	            + 2.0 * mu * strainIncrement.get(2,1));
	}

	public double getYoung() {
		return young;
	}

	public double getPoisson() {
		return poisson;
	}

	public double getLambda() {
		return lambda;
	}

	public double getMu() {
		return mu;
	}

}