package tw.edu.mpm.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import tw.edu.mpm.math.Vector3;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * ParticleSet.java
 *
 *
 * Material Point 集合管理類別
 *
 *
 * 功能：
 *
 *     1. 管理所有Particle
 *     2. 新增Particle
 *     3. 搜尋Particle
 *     4. 更新Particle
 *
 * ============================================================
 */

public class ParticleSet implements Iterable<MaterialPoint> {

	/*
	 * ========================================================
	 *
	 * Particle List
	 *
	 * ========================================================
	 */

	private List<MaterialPoint> particles;

	/*
	 * ========================================================
	 *
	 * Constructor
	 *
	 * ========================================================
	 */

	public ParticleSet() {

		particles = new ArrayList<>();

	}

	/**
	 *
	 * 指定初始容量
	 *
	 */

	public ParticleSet(int capacity) {

		particles = new ArrayList<>(capacity);

	}

	/*
	 * ========================================================
	 *
	 * Add Particle
	 *
	 * ========================================================
	 */

	public void add(MaterialPoint particle) {

		particles.add(particle);

	}

	/**
	 *
	 * 建立Particle
	 *
	 */

	public MaterialPoint create(Vector3 position) {

		MaterialPoint particle =

				new MaterialPoint(

						particles.size(),

						position

				);

		add(particle);

		return particle;

	}

	/*
	 * ========================================================
	 *
	 * Remove
	 *
	 * ========================================================
	 */

	public void remove(MaterialPoint particle) {

		particles.remove(particle);

	}

	/**
	 *
	 * 清除所有Particle
	 *
	 */

	public void clear() {

		particles.clear();

	}

	/*
	 * ========================================================
	 *
	 * Access
	 *
	 * ========================================================
	 */

	public MaterialPoint get(int index) {
		return particles.get(index);
	}

	public int size() {
		return particles.size();
	}

	public List<MaterialPoint> getParticles() {
		return particles;
	}

	/*
	 * ========================================================
	 *
	 * Particle Update
	 *
	 * ========================================================
	 */

	/**
	 *
	 * 更新所有Particle位置
	 *
	 */

	public void updatePosition(double dt) {

		for (MaterialPoint p : particles) {
			p.updatePosition(dt);
		}

	}

	/**
	 *
	 * 更新所有Particle速度
	 *
	 */

	public void updateVelocity(double dt) {

		for (MaterialPoint p : particles) {
			p.updateVelocity(dt);
		}

	}

	/**
	 *
	 * 清除加速度
	 *
	 */

	public void resetAcceleration() {

		for (MaterialPoint p : particles) {
			p.resetAcceleration();
		}

	}

	/*
	 * ========================================================
	 *
	 * Material Property操作
	 *
	 * ========================================================
	 */

	/**
	 *
	 * 設定全部Particle密度
	 *
	 */

	public void setDensity(double density) {

		for (MaterialPoint p : particles) {
			p.setDensity(density);
		}

	}

	/**
	 *
	 * 設定全部Particle質量
	 *
	 */

	public void setMass(double mass) {

		for (MaterialPoint p : particles) {
			p.setMass(mass);
		}

	}

	/*
	 * ========================================================
	 *
	 * Iterator
	 *
	 * ========================================================
	 */

	@Override

	public Iterator<MaterialPoint> iterator() {
		return particles.iterator();
	}

	/*
	 * ========================================================
	 *
	 * 搜尋Particle
	 *
	 * ========================================================
	 */

	/**
	 *
	 * 依ID搜尋
	 *
	 */

	public MaterialPoint findById(int id) {

		for (MaterialPoint p : particles) {

			if (p.getId() == id) {
				return p;
			}

		}

		return null;

	}

	/**
	 *
	 * 計算質量總和
	 *
	 *
	 * M=sum(mp)
	 *
	 */

	public double totalMass() {

		double sum = 0.0;

		for (MaterialPoint p : particles) {
			sum += p.getMass();
		}

		return sum;

	}

	/**
	 *
	 * 計算Particle中心位置
	 *
	 *
	 * xc=
	 *
	 * Σ(mx)/Σm
	 *
	 */

	public Vector3 centerOfMass() {

		Vector3 center = new Vector3();

		double totalMass = totalMass();

		if (totalMass < 1e-12) {
			return center;
		}

		for (MaterialPoint p : particles) {

			center.addInPlace(

					p.getPosition().multiply(p.getMass())

			);

		}

		return center.divide(totalMass);

	}

	/**
	 * ============================================================
	 *
	 * 加入另一個 ParticleSet
	 *
	 * 用於：
	 *
	 *     水體 + 土體
	 *     土石 + 滑動塊
	 *
	 *
	 * ============================================================
	 */

	public void addAll(ParticleSet other) {

		for (MaterialPoint p : other) {
			particles.add(p);
		}

	}

	/*
	 * ========================================================
	 *
	 * Debug Output
	 *
	 * ========================================================
	 */

	public void printInfo() {

		System.out.println("========== Particle Set ==========");

		System.out.println("Particle Count = " + particles.size());

		System.out.println("Total Mass = " + totalMass());

		System.out.println("Center = " + centerOfMass());

	}

}