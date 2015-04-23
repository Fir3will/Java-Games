package main.utils.math;

import java.io.Serializable;
import main.saving.DataTag;
import main.saving.Savable;
import main.utils.file.JavaFileHelper;

public final class Vector2F implements Cloneable, Savable, Serializable
{
	public static final Vector2F ZERO = new Vector2F(0F, 0F);
	public static final Vector2F UNIT_XY = new Vector2F(1F, 1F);
	public static final Vector2F UNIT_X = new Vector2F(1F, 0);
	public static final Vector2F UNIT_Y = new Vector2F(0, 1F);
	public static final Vector2F POSITIVE_INFINITY = new Vector2F(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
	public static final Vector2F NEGATIVE_INFINITY = new Vector2F(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
	public static final Vector2F NaN = new Vector2F(Float.NaN, Float.NaN);

	/**
	 * the x value of the vector.
	 */
	public float x;
	/**
	 * the y value of the vector.
	 */
	public float y;

	/**
	 * Creates a Vector2F with the given initial x and y values.
	 * 
	 * @param x
	 *            The x value of this Vector2F.
	 * @param y
	 *            The y value of this Vector2F.
	 */
	public Vector2F(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a Vector2F with the given initial values for both x and y.
	 * 
	 * @param value the x and y value of this Vector2F
	 */
	public Vector2F(float value)
	{
		x = y = value;
	}

	/**
	 * Creates a Vector2F with x and y set to 0. Equivalent to Vector2F(0,0).
	 */
	public Vector2F()
	{
		x = y = 0;
	}

	/**
	 * Creates a new Vector2F that contains the passed vector's information
	 * 
	 * @param vector2F
	 *            The vector to copy
	 */
	public Vector2F(Vector2F vector2F)
	{
		x = vector2F.x;
		y = vector2F.y;
	}

	/**
	 * set the x and y values of the vector
	 * 
	 * @param x
	 *            the x value of the vector.
	 * @param y
	 *            the y value of the vector.
	 * @return this vector
	 */
	public Vector2F set(float x, float y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * set the x and y values of the vector from another vector
	 * 
	 * @param vec
	 *            the vector to copy from
	 * @return this vector
	 */
	public Vector2F set(Vector2F vec)
	{
		x = vec.x;
		y = vec.y;
		return this;
	}

	/**
	 * <code>add</code> adds a provided vector to this vector creating a
	 * resultant vector which is returned. If the provided vector is null, null
	 * is returned.
	 * 
	 * @param vec
	 *            the vector to add to this.
	 * @return the resultant vector.
	 */
	public Vector2F add(Vector2F vec)
	{
		if (null == vec)
		{
			System.err.println("Provided vector is null, null returned. Called By: " + JavaFileHelper.classThatCalledAt(2).getName() + "." + JavaFileHelper.methodNameThatCalledAt(2) + "(...)");
			return null;
		}
		return new Vector2F(x + vec.x, y + vec.y);
	}

	/**
	 * <code>addLocal</code> adds a provided vector to this vector internally,
	 * and returns a handle to this vector for easy chaining of calls. If the
	 * provided vector is null, null is returned.
	 * 
	 * @param vec
	 *            the vector to add to this vector.
	 * @return this
	 */
	public Vector2F addLocal(Vector2F vec)
	{
		if (null == vec)
		{
			System.err.println("Provided vector is null, null returned. Called By: " + JavaFileHelper.classThatCalledAt(2).getName() + "." + JavaFileHelper.methodNameThatCalledAt(2) + "(...)");
			return null;
		}
		x += vec.x;
		y += vec.y;
		return this;
	}

	/**
	 * <code>addLocal</code> adds the provided values to this vector internally,
	 * and returns a handle to this vector for easy chaining of calls.
	 * 
	 * @param addX
	 *            value to add to x
	 * @param addY
	 *            value to add to y
	 * @return this
	 */
	public Vector2F addLocal(float addX, float addY)
	{
		x += addX;
		y += addY;
		return this;
	}

	/**
	 * <code>add</code>
	 * 
	 * @param addX
	 * @param addY
	 * @return
	 */
	public Vector2F add(float addX, float addY)
	{
		return new Vector2F(x + addX, y + addY);
	}

	/**
	 * <code>add</code> adds this vector by <code>vec</code> and stores the
	 * result in <code>result</code>.
	 * 
	 * @param vec
	 *            The vector to add.
	 * @param result
	 *            The vector to store the result in.
	 * @return The result vector, after adding.
	 */
	public Vector2F add(Vector2F vec, Vector2F result)
	{
		if (vec == null)
		{
			System.err.println("Provided vector is null, null returned. Called By: " + JavaFileHelper.classThatCalledAt(2).getName() + "." + JavaFileHelper.methodNameThatCalledAt(2) + "(...)");
			return null;
		}
		if (result == null)
		{
			result = new Vector2F();
		}
		result.x = x + vec.x;
		result.y = y + vec.y;
		return result;
	}

	/**
	 * <code>dot</code> calculates the dot product of this vector with a
	 * provided vector. If the provided vector is null, 0 is returned.
	 * 
	 * @param vec
	 *            the vector to dot with this vector.
	 * @return the resultant dot product of this vector and a given vector.
	 */
	public float dot(Vector2F vec)
	{
		if (null == vec)
		{
			System.err.println("Provided vector is null, 0 returned.");
			return 0;
		}
		return x * vec.x + y * vec.y;
	}

	public float determinant(Vector2F v)
	{
		return x * v.y - y * v.x;
	}

	/**
	 * Sets this vector to the interpolation by changeAmnt from this to the
	 * finalVec this=(1-changeAmnt)*this + changeAmnt * finalVec
	 * 
	 * @param finalVec
	 *            The final vector to interpolate towards
	 * @param changeAmnt
	 *            An amount between 0.0 - 1.0 representing a percentage change
	 *            from this towards finalVec
	 */
	public Vector2F interpolate(Vector2F finalVec, float changeAmnt)
	{
		x = (1 - changeAmnt) * x + changeAmnt * finalVec.x;
		y = (1 - changeAmnt) * y + changeAmnt * finalVec.y;
		return this;
	}

	/**
	 * Sets this vector to the interpolation by changeAmnt from beginVec to
	 * finalVec this=(1-changeAmnt)*beginVec + changeAmnt * finalVec
	 * 
	 * @param beginVec
	 *            The begining vector (delta=0)
	 * @param finalVec
	 *            The final vector to interpolate towards (delta=1)
	 * @param changeAmnt
	 *            An amount between 0.0 - 1.0 representing a precentage change
	 *            from beginVec towards finalVec
	 */
	public Vector2F interpolate(Vector2F beginVec, Vector2F finalVec, float changeAmnt)
	{
		x = (1 - changeAmnt) * beginVec.x + changeAmnt * finalVec.x;
		y = (1 - changeAmnt) * beginVec.y + changeAmnt * finalVec.y;
		return this;
	}

	/**
	 * Check a vector... if it is null or its floats are NaN or infinite, return
	 * false. Else return true.
	 * 
	 * @param vector
	 *            the vector to check
	 * @return true or false as stated above.
	 */
	public static boolean isValidVector(Vector2F vector)
	{
		if (vector == null) return false;
		if (Float.isNaN(vector.x) || Float.isNaN(vector.y)) return false;
		if (Float.isInfinite(vector.x) || Float.isInfinite(vector.y)) return false;
		return true;
	}

	/**
	 * <code>length</code> calculates the magnitude of this vector.
	 * 
	 * @return the length or magnitude of the vector.
	 */
	public float length()
	{
		return FastMath.sqrt(lengthSquared());
	}

	/**
	 * <code>lengthSquared</code> calculates the squared value of the magnitude
	 * of the vector.
	 * 
	 * @return the magnitude squared of the vector.
	 */
	public float lengthSquared()
	{
		return x * x + y * y;
	}

	/**
	 * <code>distanceSquared</code> calculates the distance squared between this
	 * vector and vector v.
	 * 
	 * @param v
	 *            the second vector to determine the distance squared.
	 * @return the distance squared between the two vectors.
	 */
	public float distanceSquared(Vector2F v)
	{
		final double dx = x - v.x;
		final double dy = y - v.y;
		return (float) (dx * dx + dy * dy);
	}

	/**
	 * <code>distanceSquared</code> calculates the distance squared between this
	 * vector and vector v.
	 * 
	 * @param otherX
	 *            The X coordinate of the v vector
	 * @param otherY
	 *            The Y coordinate of the v vector
	 * @return the distance squared between the two vectors.
	 */
	public float distanceSquared(float otherX, float otherY)
	{
		final double dx = x - otherX;
		final double dy = y - otherY;
		return (float) (dx * dx + dy * dy);
	}

	/**
	 * <code>distance</code> calculates the distance between this vector and
	 * vector v.
	 * 
	 * @param v
	 *            the second vector to determine the distance.
	 * @return the distance between the two vectors.
	 */
	public float distance(Vector2F v)
	{
		return FastMath.sqrt(distanceSquared(v));
	}

	/**
	 * <code>mult</code> multiplies this vector by a scalar. The resultant
	 * vector is returned.
	 * 
	 * @param scalar
	 *            the value to multiply this vector by.
	 * @return the new vector.
	 */
	public Vector2F mult(float scalar)
	{
		return new Vector2F(x * scalar, y * scalar);
	}

	/**
	 * <code>multLocal</code> multiplies this vector by a scalar internally, and
	 * returns a handle to this vector for easy chaining of calls.
	 * 
	 * @param scalar
	 *            the value to multiply this vector by.
	 * @return this
	 */
	public Vector2F multLocal(float scalar)
	{
		x *= scalar;
		y *= scalar;
		return this;
	}

	/**
	 * <code>multLocal</code> multiplies a provided vector to this vector
	 * internally, and returns a handle to this vector for easy chaining of
	 * calls. If the provided vector is null, null is returned.
	 * 
	 * @param vec
	 *            the vector to mult to this vector.
	 * @return this
	 */
	public Vector2F multLocal(Vector2F vec)
	{
		if (null == vec)
		{
			System.err.println("Provided vector is null, null returned.");
			return null;
		}
		x *= vec.x;
		y *= vec.y;
		return this;
	}

	/**
	 * Multiplies this Vector2F's x and y by the scalar and stores the result in
	 * product. The result is returned for chaining. Similar to
	 * product=this*scalar;
	 * 
	 * @param scalar
	 *            The scalar to multiply by.
	 * @param product
	 *            The vector2F to store the result in.
	 * @return product, after multiplication.
	 */
	public Vector2F mult(float scalar, Vector2F product)
	{
		if (null == product)
		{
			product = new Vector2F();
		}

		product.x = x * scalar;
		product.y = y * scalar;
		return product;
	}

	/**
	 * <code>divide</code> divides the values of this vector by a scalar and
	 * returns the result. The values of this vector remain untouched.
	 * 
	 * @param scalar
	 *            the value to divide this vectors attributes by.
	 * @return the result <code>Vector</code>.
	 */
	public Vector2F divide(float scalar)
	{
		return new Vector2F(x / scalar, y / scalar);
	}

	/**
	 * <code>divideLocal</code> divides this vector by a scalar internally, and
	 * returns a handle to this vector for easy chaining of calls. Dividing by
	 * zero will result in an exception.
	 * 
	 * @param scalar
	 *            the value to divides this vector by.
	 * @return this
	 */
	public Vector2F divideLocal(float scalar)
	{
		x /= scalar;
		y /= scalar;
		return this;
	}

	public Vector2F divideLocal(Vector2F scalar)
	{
		x /= scalar.x;
		y /= scalar.y;
		return this;
	}

	/**
	 * <code>negate</code> returns the negative of this vector. All values are
	 * negated and set to a new vector.
	 * 
	 * @return the negated vector.
	 */
	public Vector2F negate()
	{
		return new Vector2F(-x, -y);
	}

	/**
	 * <code>negateLocal</code> negates the internal values of this vector.
	 * 
	 * @return this.
	 */
	public Vector2F negateLocal()
	{
		x = -x;
		y = -y;
		return this;
	}

	/**
	 * <code>subtract</code> subtracts the values of a given vector from those
	 * of this vector creating a new vector object. If the provided vector is
	 * null, an exception is thrown.
	 * 
	 * @param vec
	 *            the vector to subtract from this vector.
	 * @return the result vector.
	 */
	public Vector2F subtract(Vector2F vec)
	{
		return subtract(vec, null);
	}

	/**
	 * <code>subtract</code> subtracts the values of a given vector from those
	 * of this vector storing the result in the given vector object. If the
	 * provided vector is null, an exception is thrown.
	 * 
	 * @param vec
	 *            the vector to subtract from this vector.
	 * @param store
	 *            the vector to store the result in. It is safe for this to be
	 *            the same as vec. If null, a new vector is created.
	 * @return the result vector.
	 */
	public Vector2F subtract(Vector2F vec, Vector2F store)
	{
		if (store == null)
		{
			store = new Vector2F();
		}
		store.x = x - vec.x;
		store.y = y - vec.y;
		return store;
	}

	/**
	 * <code>subtract</code> subtracts the given x,y values from those of this
	 * vector creating a new vector object.
	 * 
	 * @param valX
	 *            value to subtract from x
	 * @param valY
	 *            value to subtract from y
	 * @return this
	 */
	public Vector2F subtract(float valX, float valY)
	{
		return new Vector2F(x - valX, y - valY);
	}

	/**
	 * <code>subtractLocal</code> subtracts a provided vector to this vector
	 * internally, and returns a handle to this vector for easy chaining of
	 * calls. If the provided vector is null, null is returned.
	 * 
	 * @param vec
	 *            the vector to subtract
	 * @return this
	 */
	public Vector2F subtractLocal(Vector2F vec)
	{
		if (vec == null)
		{
			System.err.println("Provided vector is null, null returned");
			return null;
		}
		x -= vec.x;
		y -= vec.y;
		return this;
	}

	/**
	 * <code>subtractLocal</code> subtracts the provided values from this vector
	 * internally, and returns a handle to this vector for easy chaining of
	 * calls.
	 * 
	 * @param valX
	 *            value to subtract from x
	 * @param valY
	 *            value to subtract from y
	 * @return this
	 */
	public Vector2F subtractLocal(float valX, float valY)
	{
		x -= valX;
		y -= valY;
		return this;
	}

	/**
	 * <code>normalize</code> returns the unit vector of this vector.
	 * 
	 * @return unit vector of this vector.
	 */
	public Vector2F normalize()
	{
		final float length = length();
		if (length != 0) return divide(length);

		return divide(1);
	}

	/**
	 * <code>normalizeLocal</code> makes this vector into a unit vector of
	 * itself.
	 * 
	 * @return this.
	 */
	public Vector2F normalizeLocal()
	{
		final float length = length();
		if (length != 0) return divideLocal(length);

		return divideLocal(1);
	}

	/**
	 * <code>smallestAngleBetween</code> returns (in radians) the minimum angle
	 * between two vectors. It is assumed that both this vector and the given
	 * vector are unit vectors (iow, normalized).
	 * 
	 * @param otherVector
	 *            a unit vector to find the angle against
	 * @return the angle in radians.
	 */
	public float smallestAngleBetween(Vector2F otherVector)
	{
		final float dotProduct = dot(otherVector);
		final float angle = FastMath.acos(dotProduct);
		return angle;
	}

	/**
	 * <code>angleBetween</code> returns (in radians) the angle required to
	 * rotate a ray represented by this vector to lie colinear to a ray
	 * described by the given vector. It is assumed that both this vector and
	 * the given vector are unit vectors (iow, normalized).
	 * 
	 * @param otherVector
	 *            the "destination" unit vector
	 * @return the angle in radians.
	 */
	public float angleBetween(Vector2F otherVector)
	{
		final float angle = FastMath.atan2(otherVector.y, otherVector.x) - FastMath.atan2(y, x);
		return angle;
	}

	public float getX()
	{
		return x;
	}

	public Vector2F setX(float x)
	{
		this.x = x;
		return this;
	}

	public float getY()
	{
		return y;
	}

	public Vector2F setY(float y)
	{
		this.y = y;
		return this;
	}

	/**
	 * <code>getAngle</code> returns (in radians) the angle represented by this
	 * Vector2F as expressed by a conversion from rectangular coordinates 
	 * (<code>x</code>, <code>y</code>) to polar coordinates
	 * (r, <i>theta</i>).
	 * 
	 * @return the angle in radians. [-pi, pi)
	 */
	public float getAngle()
	{
		return FastMath.atan2(y, x);
	}

	/**
	 * <code>zero</code> resets this vector's data to zero internally.
	 */
	public Vector2F zero()
	{
		x = y = 0;
		return this;
	}

	/**
	 * <code>hashCode</code> returns a unique code for this vector object based
	 * on it's values. If two vectors are logically equivalent, they will return
	 * the same hash code value.
	 * 
	 * @return the hash code value of this vector.
	 */
	@Override
	public int hashCode()
	{
		int hash = 37;
		hash += 37 * hash + Float.floatToIntBits(x);
		hash += 37 * hash + Float.floatToIntBits(y);
		return hash;
	}

	@Override
	public Vector2F clone()
	{
		return new Vector2F(x, y);
	}

	/**
	 * Saves this Vector2F into the given float[] object.
	 * 
	 * @param floats
	 *            The float[] to take this Vector2F. If null, a new float[2] is
	 *            created.
	 * @return The array, with X, Y float values in that order
	 */
	public float[] toArray(float[] floats)
	{
		if (floats == null)
		{
			floats = new float[2];
		}
		floats[0] = x;
		floats[1] = y;
		return floats;
	}

	/**
	 * are these two vectors the same? they are is they both have the same x and
	 * y values.
	 * 
	 * @param o
	 *            the object to compare for equality
	 * @return true if they are equal
	 */
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Vector2F)) return false;

		if (this == o) return true;

		final Vector2F comp = (Vector2F) o;
		if (Float.compare(x, comp.x) != 0) return false;
		if (Float.compare(y, comp.y) != 0) return false;
		return true;
	}

	/**
	 * <code>toString</code> returns the string representation of this vector
	 * object. The format of the string is such: (x, y)
	 * 
	 * @return the string representation of this vector.
	 */
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}

	public void rotateAround(float angle, boolean cw)
	{
		if (cw)
		{
			angle = -angle;
		}
		final float newX = FastMath.cos(angle) * x - FastMath.sin(angle) * y;
		final float newY = FastMath.sin(angle) * x + FastMath.cos(angle) * y;
		x = newX;
		y = newY;
	}

	public Vector2F rotateAroundOrgin(Vector2F orgin, float angle, boolean cw)
	{
		if (cw)
		{
			angle = -angle;
		}
		final float newX = FastMath.cos(angle) * x - FastMath.sin(angle) * y;
		final float newY = FastMath.sin(angle) * x + FastMath.cos(angle) * y;
		final float x = newX + orgin.x;
		final float y = newY + orgin.y;
		return new Vector2F(x, y);
	}

	@Override
	public void loadFromTag(DataTag tag)
	{
		x = tag.getFloat("Vec X", 0.0F);
		y = tag.getFloat("Vec Y", 0.0F);
	}

	@Override
	public void saveToTag(DataTag tag)
	{
		tag.setFloat("Vec X", x);
		tag.setFloat("Vec Y", y);
	}

	public static Vector2F fromTag(DataTag tag)
	{
		Vector2F v = new Vector2F();
		v.loadFromTag(tag);
		return v;
	}

	private static final long serialVersionUID = 1;
}
