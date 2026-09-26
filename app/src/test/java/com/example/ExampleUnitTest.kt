package com.example

import com.example.math.AngleMode
import com.example.math.ScientificEvaluator
import com.example.model.DimensionVector
import com.example.parser.DimensionalParser
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testAccelerationDimensions() {
        val parsed = DimensionalParser.parseUnitExpression("m/s^2")
        assertTrue("Parsed m/s^2 should be valid", parsed.isValid)
        assertEquals("Acceleration dimension should be L*T^-2", DimensionVector(l = 1, t = -2), parsed.dimensions)
        assertEquals("a=L*T^-2", "L*T^-2", parsed.asciiFormula)
    }

    @Test
    fun testForceDimensions() {
        val parsed = DimensionalParser.parseUnitExpression("kg*m/s^2")
        assertTrue("kg*m/s^2 should be valid", parsed.isValid)
        assertEquals(DimensionVector(m = 1, l = 1, t = -2), parsed.dimensions)

        val newtonParsed = DimensionalParser.parseUnitExpression("N")
        assertEquals(parsed.dimensions, newtonParsed.dimensions)
    }

    @Test
    fun testPressureDimensions() {
        val parsed = DimensionalParser.parseUnitExpression("Pa")
        assertTrue("Pa should be valid", parsed.isValid)
        assertEquals(DimensionVector(m = 1, l = -1, t = -2), parsed.dimensions)
    }

    @Test
    fun testEquationHomogeneity() {
        val result = DimensionalParser.verifyHomogeneity("v = u + a*t")
        assertTrue("v = u + a*t should be dimensionally homogeneous", result.isHomogeneous)

        val badResult = DimensionalParser.verifyHomogeneity("v = u + a")
        assertFalse("v = u + a should not be homogeneous", badResult.isHomogeneous)
    }

    @Test
    fun testScientificEvaluatorArithmetic() {
        val res = ScientificEvaluator.evaluate("2 + 3 * 4")
        assertTrue(res.isSuccess)
        assertEquals(14.0, res.value, 1e-6)
    }

    @Test
    fun testScientificEvaluatorTrig() {
        val resDeg = ScientificEvaluator.evaluate("sin(30)", AngleMode.DEGREE)
        assertTrue(resDeg.isSuccess)
        assertEquals(0.5, resDeg.value, 1e-6)

        val resRad = ScientificEvaluator.evaluate("sin(pi / 6)", AngleMode.RADIAN)
        assertTrue(resRad.isSuccess)
        assertEquals(0.5, resRad.value, 1e-6)
    }
}
