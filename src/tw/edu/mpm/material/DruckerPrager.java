package tw.edu.mpm.material;

import tw.edu.mpm.math.Matrix3;
import tw.edu.mpm.math.Tensor3;
import tw.edu.mpm.particle.MaterialPoint;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * DruckerPrager.java
 *
 * Drucker-Prager 彈塑性材料模型
 *
 * Yield Function
 *
 *     f = sqrt(J2) + α I1 - k
 *
 * I1 = 第一應力不變量
 * J2 = 第二偏差應力不變量
 *
 * ============================================================
 */
public class DruckerPrager extends AbstractMaterial {

	/**
	 * 凝聚力 c (Pa)
	 */
	private final double cohesion;

	/**
	 * 摩擦角 φ (rad)
	 */
	private final double frictionAngle;

	/**
	 * 膨脹角 ψ (rad)
	 */
	private final double dilationAngle;

	/**
	 * Drucker-Prager 常數 α
	 */
	private final double alpha;

	/**
	 * Drucker-Prager 常數 k
	 */
	private final double k;

	/**
	 * 建構子
	 */
	public DruckerPrager(double density, double youngsModulus, double poissonsRatio, double cohesion, double frictionAngleDegree, double dilationAngleDegree) {

		super("Drucker-Prager", density, youngsModulus, poissonsRatio);

		this.cohesion = cohesion;

		this.frictionAngle = Math.toRadians(frictionAngleDegree);

		this.dilationAngle = Math.toRadians(dilationAngleDegree);

		/*
		 * Circumscribed Drucker-Prager
		 */
		double sinPhi = Math.sin(this.frictionAngle);
		double cosPhi = Math.cos(this.frictionAngle);

		this.alpha = (2.0 * sinPhi) / (Math.sqrt(3.0) * (3.0 - sinPhi));

		this.k = (6.0 * cohesion * cosPhi) / (Math.sqrt(3.0) * (3.0 - sinPhi));
	}

	/**
	 * 應力更新
	 */
	@Override
	public Tensor3 updateStress(MaterialPoint particle, Tensor3 stress, Matrix3 strainIncrement, double dt) {

		/*
		 * Elastic Predictor
		 */
		LinearElastic elastic = new LinearElastic(density, youngsModulus, poissonsRatio);

		Tensor3 trialStress = elastic.updateStress(particle, stress, strainIncrement, dt);

		/*
		 * Yield Check
		 */
		if (!hasYielded(trialStress)) {
			return trialStress;
		}

		/*
		 * Plastic Correction
		 *
		 * 待 PlasticIntegrator 完成
		 */
		return plasticCorrection(trialStress);
	}

	/**
	 * Drucker-Prager Yield Function
	 */
	@Override
	public boolean hasYielded(Tensor3 stress) {

		double I1 = stress.trace();

		Tensor3 s = stress.deviatoric();

		double J2 = 0.5 * s.doubleDot(s);

		double f = Math.sqrt(J2) + alpha * I1 - k;

		return f > 0.0;
	}

	/**
	 * Plastic Correction
	 *
	 * 暫時回傳 Trial Stress
	 */
	private Tensor3 plasticCorrection(Tensor3 trialStress) {

		/*
		 * TODO:
		 * Return Mapping
		 */
		return trialStress;
	}

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

	public double getAlpha() {
		return alpha;
	}

	public double getK() {
		return k;
	}

}