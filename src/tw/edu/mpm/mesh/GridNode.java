package tw.edu.mpm.mesh;

import tw.edu.mpm.math.Vector3;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * GridNode.java
 *
 *
 * Background Grid Node
 *
 *
 * MPM中的網格節點
 *
 *
 * 儲存：
 *
 *     mass
 *     velocity
 *     momentum
 *     force
 *     boundary condition
 *
 *
 * ============================================================
 */

public class GridNode {

	/*
	 * ========================================================
	 * Node ID
	 * ========================================================
	 */

	private int id;

	/*
	 * ========================================================
	 * Node位置
	 *
	 * x,y,z
	 *
	 * ========================================================
	 */

	private Vector3 position;

	/*
	 * ========================================================
	 *
	 * Mass
	 *
	 * m_i
	 *
	 * ========================================================
	 */

	private double mass;

	/*
	 * ========================================================
	 *
	 * Velocity
	 *
	 * v_i
	 *
	 * ========================================================
	 */

	private Vector3 velocity;

	/*
	 * ========================================================
	 *
	 * Momentum
	 *
	 * p_i=m_i*v_i
	 *
	 * ========================================================
	 */

	private Vector3 momentum;

	/*
	 * ========================================================
	 *
	 * Force
	 *
	 * ========================================================
	 */

	private Vector3 internalForce;

	private Vector3 externalForce;

	/*
	 * ========================================================
	 *
	 * Boundary Condition
	 *
	 * ========================================================
	 */

	/*
	 * 是否固定
	 */

	private boolean fixed;

	/*
	 * 固定方向
	 *
	 * true表示該方向不可移動
	 *
	 */

	private boolean fixX;

	private boolean fixY;

	private boolean fixZ;

	/*
	 * ========================================================
	 *
	 * Constructor
	 *
	 * ========================================================
	 */

	public GridNode(int id, Vector3 position) {

		this.id = id;

		this.position = new Vector3(position);

		this.mass = 0.0;

		this.velocity = new Vector3();

		this.momentum = new Vector3();

		this.internalForce = new Vector3();

		this.externalForce = new Vector3();

		this.fixed = false;

		this.fixX = false;

		this.fixY = false;

		this.fixZ = false;

	}

	/*
	 * ========================================================
	 *
	 * Reset
	 *
	 * 每個時間步開始前清除
	 *
	 * ========================================================
	 */

	public void reset() {

		mass = 0.0;

		velocity = new Vector3();

		momentum = new Vector3();

		internalForce = new Vector3();

		externalForce = new Vector3();

	}

	/*
	 * ========================================================
	 *
	 * Particle → Grid
	 *
	 * 質量累積
	 *
	 * ========================================================
	 */

	public void addMass(double dm) {

		mass += dm;

	}

	/*
	 * ========================================================
	 *
	 * 動量累積
	 *
	 * ========================================================
	 */

	public void addMomentum(Vector3 dp) {

		momentum.addInPlace(dp);

	}

	/*
	 * ========================================================
	 *
	 * 力累積
	 *
	 * ========================================================
	 */

	public void addInternalForce(Vector3 force) {

		internalForce.addInPlace(force);

	}

	public void addExternalForce(Vector3 force) {

		externalForce.addInPlace(force);

	}

	/*
	 * ========================================================
	 *
	 * 更新速度
	 *
	 * v=p/m
	 *
	 * ========================================================
	 */

	public void updateVelocity() {

		if (mass > 1e-12) {

			velocity = momentum.divide(mass);

		} else {

			velocity = new Vector3();

		}

	}

	/*
	 * ========================================================
	 *
	 * Momentum Update
	 *
	 *
	 * p(n+1)
	 *
	 * =
	 *
	 * p(n)+fΔt
	 *
	 * ========================================================
	 */

	public void updateMomentum(double dt) {

		//Vector3 totalForce = internalForce.add(externalForce);
		Vector3 totalForce = internalForce;
		totalForce.addInPlace(externalForce);
		momentum.addInPlace(totalForce.multiply(dt));

	}

	/*
	 * ========================================================
	 *
	 * Boundary Condition
	 *
	 * ========================================================
	 */

	public void setFixed() {

		fixed = true;

		fixX = true;

		fixY = true;

		fixZ = true;

	}

	public void setFixedX() {

		fixX = true;

	}

	public void setFixedY() {

		fixY = true;

	}

	public void setFixedZ() {

		fixZ = true;

	}

	/**
	 *
	 * 套用Boundary
	 *
	 */

	public void applyBoundary() {

		if (fixX) {

			velocity.x = 0;

			momentum.x = 0;

		}

		if (fixY) {

			velocity.y = 0;

			momentum.y = 0;

		}

		if (fixZ) {

			velocity.z = 0;

			momentum.z = 0;

		}

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

	public double getMass() {

		return mass;

	}

	public Vector3 getVelocity() {

		return velocity;

	}

	public Vector3 getMomentum() {

		return momentum;

	}

	public Vector3 getInternalForce() {

		return internalForce;

	}

	public Vector3 getExternalForce() {

		return externalForce;

	}

	public boolean isFixed() {

		return fixed;

	}

	/*
	 * ========================================================
	 *
	 * Output
	 *
	 * ========================================================
	 */

	@Override

	public String toString() {

		return "Node " + id + "\nPosition=" + position + "\nMass=" + mass + "\nVelocity=" + velocity;

	}

}