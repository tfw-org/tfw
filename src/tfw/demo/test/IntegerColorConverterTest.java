package tfw.demo.test;

import java.awt.Color;
import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import tfw.demo.IntegerColorConverter;
import tfw.demo.RedGreenBlueECD;
import tfw.tsm.ecd.ColorECD;

public class IntegerColorConverterTest extends TestCase
{
	private static final RedGreenBlueECD RED_NAME = new RedGreenBlueECD("red");
	private static final RedGreenBlueECD GREEN_NAME = new RedGreenBlueECD("green");
	private static final RedGreenBlueECD BLUE_NAME = new RedGreenBlueECD("blue");
	private static final ColorECD COLOR_NAME = new ColorECD("color");

	private final HashMap integerMap = new HashMap();
	private final HashMap colorMap = new HashMap();
	private final HashMap stateMap = new HashMap();

	IntegerColorConverter icc = new IntegerColorConverter(
		"Test Converter",
		RED_NAME, BLUE_NAME, GREEN_NAME, COLOR_NAME);
	public void setUp()
	{
		integerMap.put(RED_NAME, new Integer(0));
		integerMap.put(GREEN_NAME, new Integer(1));
		integerMap.put(BLUE_NAME, new Integer(2));

		colorMap.put(COLOR_NAME, new Color(0, 1, 2));

		stateMap.putAll(integerMap);
		stateMap.putAll(colorMap);
	}
//
//	public void testIntegerToColorConversion()
//	{
//		BranchTester nt = new BranchTester(icc);
//
//		nt.set(integerMap);
//		assertEquals(stateMap, nt.get());
//	}
//
//	public void testColorToIntegerConversion()
//	{
//		BranchTester nt = new BranchTester(icc);
//
//		nt.set(colorMap);
//		assertEquals(stateMap, nt.get());
//	}

	public static Test suite()
	{
		return (new TestSuite(IntegerColorConverterTest.class));
	}

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}
}
