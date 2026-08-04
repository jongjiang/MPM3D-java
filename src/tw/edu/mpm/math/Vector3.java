package tw.edu.mpm.math;


/**
 * ============================================================
 * MPM3D-Java
 *
 * Vector3.java
 *
 * 三維向量類別
 *
 * 用於：
 *
 *     1. Particle位置
 *     2. Velocity
 *     3. Acceleration
 *     4. Force
 *     5. Momentum
 *
 *
 * Vector:
 *
 *        | x |
 *    v = | y |
 *        | z |
 *
 *
 * ============================================================
 */


public class Vector3 {


    /*
     * ========================================================
     * 成員變數
     * ========================================================
     */


    /**
     * X方向分量
     */
    public double x;


    /**
     * Y方向分量
     */
    public double y;


    /**
     * Z方向分量
     */
    public double z;



    /*
     * ========================================================
     * 建構子 Constructor
     * ========================================================
     */


    /**
     * 建立零向量
     *
     * Vector = (0,0,0)
     */
    public Vector3(){

        this.x = 0.0;

        this.y = 0.0;

        this.z = 0.0;

    }




    /**
     * 建立指定向量
     *
     * @param x X分量
     * @param y Y分量
     * @param z Z分量
     */

    public Vector3(
            double x,
            double y,
            double z
    ){

        this.x = x;

        this.y = y;

        this.z = z;

    }

    /*
     * ========================================================
     *
     * Set value
     *
     * ========================================================
     */


    public void set(

            double x,

            double y,

            double z

    ){


        this.x=x;

        this.y=y;

        this.z=z;


    }


    /**
     * 複製另一個Vector3
     *
     * @param other 原向量
     */

    public Vector3(Vector3 other){

        this.x = other.x;

        this.y = other.y;

        this.z = other.z;

    }





    /*
     * ========================================================
     * 基本運算
     * ========================================================
     */



    /**
     * 向量加法
     *
     * v = a + b
     *
     */

    public Vector3 add(Vector3 v){


        return new Vector3(

                this.x + v.x,

                this.y + v.y,

                this.z + v.z

        );

    }





    /**
     * 向量減法
     *
     * v = a - b
     */

    public Vector3 subtract(Vector3 v){


        return new Vector3(

                this.x - v.x,

                this.y - v.y,

                this.z - v.z

        );

    }






    /**
     * 純量乘法
     *
     * v = c*a
     *
     * MPM常用：
     *
     * mass * velocity
     *
     */

    public Vector3 multiply(double scalar){


        return new Vector3(

                scalar * x,

                scalar * y,

                scalar * z

        );

    }






    /**
     * 純量除法
     */

    public Vector3 divide(double scalar){


        if(Math.abs(scalar)<1e-12){

            throw new ArithmeticException(
                    "Vector除以零"
            );

        }


        return new Vector3(

                x/scalar,

                y/scalar,

                z/scalar

        );

    }







    /*
     * ========================================================
     * 向量內積
     * ========================================================
     */


    /**
     *
     * Dot Product
     *
     *
     * a·b =
     *
     * axbx + ayby + azbz
     *
     */

    public double dot(Vector3 v){


        return

                this.x*v.x +

                this.y*v.y +

                this.z*v.z;

    }





    /*
     * ========================================================
     * 向量外積
     * ========================================================
     */



    /**
     *
     * Cross Product
     *
     *
     * a × b
     *
     */

    public Vector3 cross(Vector3 v){


        return new Vector3(

                this.y*v.z - this.z*v.y,


                this.z*v.x - this.x*v.z,


                this.x*v.y - this.y*v.x

        );

    }







    /*
     * ========================================================
     * 長度
     * ========================================================
     */



    /**
     *
     * |v|
     *
     */

    public double magnitude(){


        return Math.sqrt(

                x*x +

                y*y +

                z*z

        );

    }






    /**
     * 長度平方
     *
     * 避免sqrt，提高效率
     */

    public double magnitudeSquared(){


        return

                x*x +

                y*y +

                z*z;

    }






    /*
     * ========================================================
     * 單位向量
     * ========================================================
     */



    /**
     *
     * normalize
     *
     * v/|v|
     *
     */

    public Vector3 normalize(){


        double length = magnitude();



        if(length < 1e-12){


            return new Vector3();


        }



        return divide(length);

    }







    /*
     * ========================================================
     * 修改目前Vector
     * ========================================================
     */



    /**
     * 累加
     *
     * this += v
     *
     */

    public void addInPlace(Vector3 v){


        this.x += v.x;

        this.y += v.y;

        this.z += v.z;


    }






    /**
     *
     * this *= scalar
     *
     */

    public void multiplyInPlace(
            double scalar
    ){


        this.x *= scalar;

        this.y *= scalar;

        this.z *= scalar;


    }








    /*
     * ========================================================
     * MPM常用函數
     * ========================================================
     */



    /**
     *
     * 計算兩點距離
     *
     * |a-b|
     *
     */

    public static double distance(
            Vector3 a,
            Vector3 b
    ){


        return a.subtract(b)
                .magnitude();

    }





    /**
     * 取得零向量
     */

    public static Vector3 zero(){


        return new Vector3();

    }





    /**
     * 字串輸出
     */

    @Override

    public String toString(){


        return String.format(

                "(%.6f , %.6f , %.6f)",

                x,

                y,

                z

        );

    }





}