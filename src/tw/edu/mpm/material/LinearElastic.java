package tw.edu.mpm.material;

import tw.edu.mpm.math.Matrix3;
import tw.edu.mpm.math.Tensor3;
import tw.edu.mpm.particle.MaterialPoint;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * LinearElastic.java
 *
 * 三維各向同性線彈性材料
 *
 * Constitutive equation:
 *
 * Δσ = λ tr(Δε) I + 2 μ Δε
 *
 *
 * λ : Lamé first parameter
 *
 * μ : Shear modulus
 *
 *
 * ============================================================
 */
public class LinearElastic extends AbstractMaterial {

	/**
	 * Constructor 建構子
	 *
	 * @param density 密度 kg/m3
	 * @param E       Young modulus Pa
	 * @param nu      Poisson ratio
	 */
	public LinearElastic(double density, double E, double nu) {
		super("Linear Elastic",	density, E,	nu);
	}

	/**
	 * ========================================================
	 *
	 * Stress update
	 *
	 * σ(n+1) = σ(n) + Δσ
	 *
	 * ========================================================
	 */
	@Override
	public Tensor3 updateStress(MaterialPoint particle,	Tensor3 stress,	Matrix3 strainIncrement, double dt) {

		/*
		 * Lamé constants
		 */

		double lambda = getLameLambda();

		double mu = getLameMu();

		/*
		 * strain tensor
		 */

		Tensor3 strain = Tensor3.fromMatrix(strainIncrement);

		/*
		 * Δσ = λ tr(Δε) I + 2μΔε
		 */

		Tensor3 deltaStress = strain.multiply(2.0 * mu);

		/*
		 * λ tr(ε) I
		 */

		deltaStress.addDiagonal( lambda * strain.trace() );

		/*
		 * σ(n+1)
		 */

		Tensor3 newStress =	stress.add(deltaStress);

		return newStress;

	}

	/**
	 * 線彈性無塑性
	 */
	@Override
	public boolean isPlastic() {
		return false;
	}

	/**
	 * 線彈性永不降伏
	 */
	@Override
	public boolean hasYielded(Tensor3 stress) {
		return false;
	}

}