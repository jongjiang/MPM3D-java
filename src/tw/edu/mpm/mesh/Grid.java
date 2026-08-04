package tw.edu.mpm.mesh;

import java.util.ArrayList;
import java.util.List;

import tw.edu.mpm.math.Vector3;
import tw.edu.mpm.util.Constants;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * Grid.java
 *
 *
 * Background Grid Manager
 *
 *
 * 功能：
 *
 *     1. 建立3D背景網格
 *     2. 管理GridNode
 *     3. 管理Cell
 *     4. 搜尋Particle所在Cell
 *     5. 提供P2G/G2P資料
 *
 *
 * ============================================================
 */

public class Grid {

	/*
	 * ========================================================
	 *
	 * Grid尺寸
	 *
	 * ========================================================
	 */

	private int nx;

	private int ny;

	private int nz;

	/*
	 * ========================================================
	 *
	 * Cell大小
	 *
	 * ========================================================
	 */

	private double dx;

	/*
	 * ========================================================
	 *
	 * Node集合
	 *
	 * ========================================================
	 */

	private List<GridNode> nodes;

	/*
	 * ========================================================
	 *
	 * Cell集合
	 *
	 * ========================================================
	 */

	private List<Cell> cells;

	/*
	 * ========================================================
	 *
	 * Constructor
	 *
	 * ========================================================
	 */

	public Grid() {

		this(

				Constants.GRID_NX,

				Constants.GRID_NY,

				Constants.GRID_NZ,

				Constants.GRID_SIZE

		);

	}

	public Grid(int nx, int ny, int nz, double dx) {

		this.nx = nx;

		this.ny = ny;

		this.nz = nz;

		this.dx = dx;

		nodes = new ArrayList<>();

		cells = new ArrayList<>();

		buildNodes();

		buildCells();

	}

	/*
	 * ========================================================
	 *
	 * 建立 Grid Nodes
	 *
	 *
	 * Node數量：
	 *
	 * (nx+1)(ny+1)(nz+1)
	 *
	 * ========================================================
	 */

	private void buildNodes() {

		int id = 0;

		for (int k = 0; k <= nz; k++) {

			for (int j = 0; j <= ny; j++) {

				for (int i = 0; i <= nx; i++) {

					Vector3 position =

							new Vector3(

									i * dx,

									j * dx,

									k * dx

							);

					GridNode node =

							new GridNode(

									id,

									position

							);

					nodes.add(node);

					id++;

				}

			}

		}

	}

	/*
	 * ========================================================
	 *
	 * 建立 Cell
	 *
	 *
	 * 每個Cell包含8個Node
	 *
	 * ========================================================
	 */

	private void buildCells() {

		int id = 0;

		for (int k = 0; k < nz; k++) {

			for (int j = 0; j < ny; j++) {

				for (int i = 0; i < nx; i++) {

					GridNode[] cellNodes =

							new GridNode[8];

					cellNodes[0] = getNode(i, j, k);

					cellNodes[1] = getNode(i + 1, j, k);

					cellNodes[2] = getNode(i + 1, j + 1, k);

					cellNodes[3] = getNode(i, j + 1, k);

					cellNodes[4] = getNode(i, j, k + 1);

					cellNodes[5] = getNode(i + 1, j, k + 1);

					cellNodes[6] = getNode(i + 1, j + 1, k + 1);

					cellNodes[7] = getNode(i, j + 1, k + 1);

					Cell cell =

							new Cell(

									id,

									cellNodes

							);

					cells.add(cell);

					id++;

				}

			}

		}

	}

	/*
	 * ========================================================
	 *
	 * Node Index
	 *
	 *
	 * 3D → 1D
	 *
	 * ========================================================
	 */

	private int nodeIndex(int i, int j, int k) {

		return

		k * (ny + 1) * (nx + 1)

				+

				j * (nx + 1)

				+

				i;

	}

	/*
	 * ========================================================
	 *
	 * 取得Node
	 *
	 * ========================================================
	 */

	public GridNode getNode(int i, int j, int k) {

		return

		nodes.get(

				nodeIndex(i, j, k)

		);

	}

	/*
	 * ========================================================
	 *
	 * Particle搜尋Cell
	 *
	 *
	 * 目前使用Linear Search
	 *
	 * 後續可改：
	 *
	 *     Hash Grid
	 *     Spatial Index
	 *
	 * ========================================================
	 */

	public Cell findCell(Vector3 position) {

		for (Cell cell : cells) {

			if (cell.contains(position)) {

				return cell;

			}

		}

		return null;

	}

	/*
	 * ========================================================
	 *
	 * 清除Grid狀態
	 *
	 *
	 * 每個時間步開始
	 *
	 * ========================================================
	 */

	public void reset() {

		for (GridNode node : nodes) {

			node.reset();

		}

	}

	/*
	 * ========================================================
	 *
	 * Apply Boundary
	 *
	 * ========================================================
	 */

	public void applyBoundary() {

		for (GridNode node : nodes) {

			node.applyBoundary();

		}

	}

	/*
	 * ========================================================
	 *
	 * Getter
	 *
	 * ========================================================
	 */

	public List<GridNode> getNodes() {

		return nodes;

	}

	/*
	 * ========================================================
	 *
	 * Get Node
	 *
	 * ========================================================
	 */

	public GridNode getNode(

			int id

	) {

		return nodes.get(id);

	}

	/*
	 * ========================================================
	 *
	 * Get Cell
	 *
	 * ========================================================
	 */

	public Cell getCell(int id) {

		return cells.get(id);

	}

	public List<Cell> getCells() {

		return cells;

	}

	public int getNodeCount() {

		return nodes.size();

	}

	public int getCellCount() {

		return cells.size();

	}

	public double getCellSize() {

		return dx;

	}

	public int getNx() {

		return nx;

	}

	public int getNy() {

		return ny;

	}

	public int getNz() {

		return nz;

	}

	/*
	 * ========================================================
	 *
	 * 輸出資訊
	 *
	 * ========================================================
	 */

	public void printInfo() {

		System.out.println("========== Grid Information ==========");

		System.out.println("Dimension = 3D");

		System.out.println("Grid = " + nx + " x " + ny + " x " + nz);

		System.out.println("Node Count = " + nodes.size());

		System.out.println("Cell Count = " + cells.size());

		System.out.println("Cell Size = " + dx);

	}

}