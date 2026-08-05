package tw.edu.mpm.material;

import tw.edu.mpm.math.Matrix3;
import tw.edu.mpm.math.Tensor3;
import tw.edu.mpm.particle.MaterialPoint;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * MohrCoulomb.java
 *
 * Mohr-Coulomb 土壤彈塑性模型
 *
 * Yield function:
 *
 * f = σ1 - σ3*Nphi - 2c*sqrt(Nphi)
 *
 * Nphi = (1+sin(phi))/(1-sin(phi))
 *
 * ============================================================
 */
public class MohrCoulomb extends AbstractMaterial {

	/*
	 * --------------------------------------------------------
	 * Soil parameters
	 * --------------------------------------------------------
	 */

	/**
	 * Cohesion c (Pa)
	 */
	private double cohesion;

	/**
	 * Friction angle φ (radian)
	 */
	private double frictionAngle;

	/**
	 * Dilation angle ψ (radian)
	 */
	private double dilationAngle;

	/**
	 * Constructor
	 */
	public MohrCoulomb(double density, double E, double nu, double cohesion, double frictionAngleDegree, double dilationAngleDegree) {

		super("Mohr-Coulomb", density, E, nu);
		this.cohesion = cohesion;
		this.frictionAngle = Math.toRadians(frictionAngleDegree);
		this.dilationAngle = Math.toRadians(dilationAngleDegree);

	}

	/**
	 * ========================================================
	 *
	 * Stress update
	 *
	 *
	 * Elastic predictor
	 *
	 * +
	 *
	 * Plastic correction
	 *
	 *
	 * ========================================================
	 */
	@Override
	public Tensor3 updateStress(MaterialPoint particle, Tensor3 stress, Matrix3 strainIncrement, double dt) {

		/*
		 * Step 1:
		 *
		 * Elastic trial stress
		 *
		 */

		LinearElastic elastic = new LinearElastic(density, youngsModulus, poissonsRatio);

		Tensor3 trialStress = elastic.updateStress(particle, stress, strainIncrement, dt);

		/*
		 * Step 2:
		 *
		 * Yield check
		 *
		 */

		if (!hasYielded(trialStress)) {
			return trialStress;
		}

		/*
		 * Step 3:
		 *
		 * Plastic correction
		 *
		 * 目前先保留接口 待 PlasticIntegrator 完成
		 *
		 */

		return plasticCorrection(trialStress);

	}

	/**
	 * ========================================================
	 *
	 * Mohr-Coulomb yield function
	 *
	 *
	 * f > 0  : plastic
	 *
	 * f <= 0 : elastic
	 *
	 * ========================================================
	 */
	@Override
	public boolean hasYielded(Tensor3 stress) {

		double[] principal = principalStress(stress);

		double sigma1 = principal[0];

		double sigma3 = principal[2];

		double sinPhi = Math.sin(frictionAngle);

		double Nphi = (1.0 + sinPhi) / (1.0 - sinPhi);

		double f = sigma1 - sigma3 * Nphi - 2.0 * cohesion * Math.sqrt(Nphi);

		return f > 0.0;

	}

	/**
	 * ========================================================
	 *
	 * Plastic correction
	 *
	 * Return Mapping 預留
	 *
	 * ========================================================
	 */
	private Tensor3 plasticCorrection(Tensor3 trial) {

		/*
		 * TODO:
		 *
		 * 完成 PlasticIntegrator.java 後
		 *
		 * 改為 Newton Return Mapping
		 *
		 */
		return trial;

	}

	/**
	 * ========================================================
	 *
	 * Principal stress
	 *
	 * 目前簡化版本
	 *
	 * 後續可替換 Jacobi Eigen Solver
	 *
	 * ========================================================
	 */
	private double[] principalStress(Tensor3 stress) {

		double s1 = stress.get(0, 0);
		double s2 = stress.get(1, 1);
		double s3 = stress.get(2, 2);
		double[] result = { s1, s2, s3 };

		/*
		 * 排序
		 *
		 * sigma1 >= sigma2 >= sigma3
		 */

		for (int i = 0; i < 3; i++) {

			for (int j = i + 1; j < 3; j++) {

				if (result[i] < result[j]) {
					double temp = result[i];
					result[i] = result[j];
					result[j] = temp;
				}

			}

		}

		return result;

	}

	/**
	 * Plastic material
	 */
	@Override
	public boolean isPlastic() {
		return true;
	}

	public double getCohesion() {
		return cohesion;
	}

	public double getFrictionAngle() {
		return frictionAngle;
	}

	public double getDilationAngle() {
		return dilationAngle;
	}

}