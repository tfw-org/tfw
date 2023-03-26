package tfw.visualizer.graph;

import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;

public class GraphECD extends ObjectECD
{
	public GraphECD(String name)
	{
		super(name, ClassValueConstraint.getInstance(Graph.class));
	}
}