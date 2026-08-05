package tw.edu.mpm.particle;

import tw.edu.mpm.math.Vector3;
import tw.edu.mpm.math.Matrix3;
import tw.edu.mpm.math.Tensor3;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * MaterialPoint.java
 *
 *
 * Material Point (物質點)
 *
 *
 * MPM核心資料結構
 *
 *
 * 儲存：
 *
 *     Position       位置
 *     Velocity       速度
 *     Acceleration   加速度
 *
 *     Mass           質量
 *     Volume         體積
 *     Density        密度
 *
 *     Stress         應力
 *     Strain         應變
 *
 *     Deformation Gradient F
 *
 * ============================================================
 */

public class MaterialPoint {

	/*
	 * ========================================================
	 *
	 * Particle ID
	 *
	 * ========================================================
	 */

	private int id;

	/*
	 * ========================================================
	 *
	 * Kinematics
	 *
	 * ========================================================
	 */

	/*
	 * 位置 x
	 */

	private Vector3 position;

	/*
	 * 速度 v
	 */

	private Vector3 velocity;

	/*
	 * 加速度 a
	 */

	private Vector3 acceleration;

	/*
	 * ========================================================
	 *
	 * Material Property
	 *
	 * ========================================================
	 */

	/*
	 * 質量 m
	 */

	private double mass;

	/*
	 * 體積 V
	 */

	private double volume;

	/*
	 * 密度 rho
	 */

	private double density;

	/*
	 * ========================================================
	 *
	 * Stress / Strain
	 *
	 * ========================================================
	 */

	/*
	 * Stress Tensor sigma
	 */

	private Tensor3 stress;

	/*
	 * Strain Tensor epsilon
	 */

	private Tensor3 strain;

	/*
	 * ========================================================
	 * Deformation Gradient F
	 * ========================================================
	 */

	private Matrix3 deformationGradient;

	/*
	 * ========================================================
	 * Constructor
	 * ========================================================
	 */

	public MaterialPoint(int id, Vector3 position) {

		this.id = id;

		this.position = new Vector3(position);

		this.velocity = new Vector3();

		this.acceleration = new Vector3();

		this.mass = 0.0;

		this.volume = 0.0;

		this.density = 0.0;

		this.stress = new Tensor3().zero();

		this.strain = new Tensor3().zero();

		/*
		 * 初始狀態： F = I
		 */

		this.deformationGradient = Matrix3.identityMatrix();

	}

	/*
	 * ========================================================
	 *
	 * Momentum Update
	 *
	 *
	 * v(n+1)=v(n)+aΔt
	 *
	 * ========================================================
	 */

	public void updateVelocity(double dt) {

		velocity.addInPlace(

				acceleration.multiply(dt)

		);

	}

	/*
	 * ========================================================
	 *
	 * Position Update
	 *
	 *
	 * x(n+1)=x(n)+vΔt
	 *
	 * ========================================================
	 */

	public void updatePosition(double dt) {

		position.addInPlace(

				velocity.multiply(dt)

		);

	}

	/*
	 * ========================================================
	 *
	 * Reset acceleration
	 *
	 * ========================================================
	 */

	public void resetAcceleration() {

		acceleration = new Vector3();

	}

	/*
	 * ========================================================
	 *
	 * Density update
	 *
	 *
	 * rho=m/V
	 *
	 * ========================================================
	 */

	public void updateDensity() {

		if (volume > 1e-12) {

			density = mass / volume;

		}

	}

	/*
	 * ========================================================
	 *
	 * Set Material Property
	 *
	 * ========================================================
	 */

	public void setMass(double mass) {

		this.mass = mass;

	}

	public void setVolume(double volume) {

		this.volume = volume;

	}

	public void setDensity(double density) {

		this.density = density;

	}

	/*
	 * ========================================================
	 *
	 * Getter
	 *
	 * ========================================================
	 */

	public int getId() {

		return id;

	}

	public Vector3 getPosition() {

		return position;

	}

	public Vector3 getVelocity() {

		return velocity;

	}

	public Vector3 getAcceleration() {

		return acceleration;

	}

	public double getMass() {

		return mass;

	}

	public double getVolume() {

		return volume;

	}

	public double getDensity() {

		return density;

	}

	public Tensor3 getStress() {

		return stress;

	}

	public Tensor3 getStrain() {

		return strain;

	}

	public Matrix3 getDeformationGradient() {

		return deformationGradient;

	}

	/*
	 * ========================================================
	 *
	 * Setter
	 *
	 * ========================================================
	 */

	public void setVelocity(Vector3 velocity) {

		this.velocity = velocity;

	}

	public void setStress(Tensor3 stress) {

		this.stress = stress;

	}

	public void setStrain(Tensor3 strain) {

		this.strain = strain;

	}

	@Override

	public String toString() {

		return

				"Particle ID=" + id +

				"\nPosition=" + position +

				"\nVelocity=" + velocity +

				"\nMass=" + mass +

				"\nDensity=" + density;

	}

}