package tfw.visualizer;

import java.awt.Color;

import tfw.awt.ecd.GraphicECD;
import tfw.awt.graphic.DrawLineGraphic;
import tfw.awt.graphic.Graphic;
import tfw.awt.graphic.SetColorGraphic;
import tfw.immutable.ilm.intilm.IntIlm;
import tfw.tsm.Converter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ilm.IntIlmECD;
import tfw.visualizer.graph.Graph;
import tfw.visualizer.graph.GraphECD;

public class EdgeToGraphicConverter extends Converter
{
	private final GraphECD graphECD;
	private final IntIlmECD pixelNodeTLBRECD;
	private final GraphicECD graphicECD;
	
	public EdgeToGraphicConverter(GraphECD graphECD, IntIlmECD pixelNodeTLBRECD,
		GraphicECD graphicECD)
	{
		super("EdgeToGraphicConverter",
			new ObjectECD[] {graphECD, pixelNodeTLBRECD},
			null,
			new ObjectECD[] {graphicECD});

		this.graphECD = graphECD;
		this.pixelNodeTLBRECD = pixelNodeTLBRECD;
		this.graphicECD = graphicECD;
	}
	
	protected void convert()
	{
		Graph graph = (Graph)get(graphECD);
		
		int[] tops = null;
		int[] lefts = null;
		int[] bottoms = null;
		int[] rights = null;
		Object[] nodes = new Object[(int)graph.nodesLength()];
		Object[] froms = new Object[(int)graph.edgesLength()];
		Object[] tos = new Object[(int)graph.edgesLength()];
		Graphic graphic = SetColorGraphic.create(null, Color.white);
		
		try
		{
//			int[][] pixelNodeTLBR = ((IntIlm)get(pixelNodeTLBRECD)).toArray();
//			tops = pixelNodeTLBR[0];
//			lefts = pixelNodeTLBR[1];
//			bottoms = pixelNodeTLBR[2];
//			rights = pixelNodeTLBR[3];

			graph.toArray(nodes, 0, 0, (int)graph.nodesLength(),
				froms, tos, 0, 0, (int)graph.edgesLength());
		}
		catch (Exception e)
		{
			return;
		}
		
		for (int i=0 ; i < froms.length ; i++)
		{
			if (froms[i] != null)
			{
				int from = -1;
				for (int j=0 ; j < nodes.length ; j++)
				{
					if (froms[i].equals(nodes[j]))
					{
						from = j;
						break;
					}
				}
				if (from == -1)
				{
					System.out.println("could not find "+froms[i]);
					continue;
				}
				
				int to = -1;
				for (int j=0 ; j < nodes.length ; j++)
				{
					if (tos[i].equals(nodes[j]))
					{
						to = j;
						break;
					}
				}
				if (to == -1)
				{
					System.out.println("could not find "+tos[i]);
					continue;
				}
				
				graphic = DrawLineGraphic.create(graphic,
					lefts[from] + (rights[from] - lefts[from]) / 2, bottoms[from],
					lefts[to] + (rights[to] - lefts[to]) / 2, tops[to]);
			}
		}

		set(graphicECD, graphic);
	}
}