/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * witout even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.visualizer;

import java.awt.FontMetrics;
import java.util.HashMap;
import java.util.Map;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.immutable.ilm.intilm.IntIlm;
import tfw.tsm.Proxy;
import tfw.visualizer.graph.Graph;

public final class PixelNodeBoundsFromNormalizedXYs
{
	private PixelNodeBoundsFromNormalizedXYs() {}
	
	public static IntIlm create(Graph graph, DoubleIlm normalizedXYs,
		int graphXOffset, int graphYOffset, int graphWidth, int graphHeight,
		FontMetrics fontMetrics)
	{
		return(new MyIntIlm(graph, normalizedXYs, graphXOffset, graphYOffset,
			graphWidth, graphHeight, fontMetrics));
	}
	
	private static class MyIntIlm implements IntIlm, ImmutableProxy
	{
		private final Graph graph;
		private final DoubleIlm normalizedXYs;
		private final int graphXOffset;
		private final int graphYOffset;
		private final int graphWidth;
		private final int graphHeight;
		private final FontMetrics fontMetrics;
		
		public MyIntIlm(Graph graph, DoubleIlm normalizedXYs, int graphXOffset,
			int graphYOffset, int graphWidth, int graphHeight,
			FontMetrics fontMetrics)
		{
			this.graph = graph;
			this.normalizedXYs = normalizedXYs;
			this.graphXOffset = graphXOffset;
			this.graphYOffset = graphYOffset;
			this.graphWidth = graphWidth;
			this.graphHeight = graphHeight;
			this.fontMetrics = fontMetrics;
		}
	    public long width()
	    {
	    	return(normalizedXYs.width());
	    }
	    
	    public long height()
	    {
	    	return(4);
	    }
	    
	    public int[][] toArray()
	    	throws DataInvalidException
    	{
	    	Object[] nodes = new Object[(int)graph.nodesLength()];
	    	Object[] froms = new Object[0];
	    	Object[] tos = new Object[0];
	    	
	    	graph.toArray(nodes, 0, 0, (int)graph.nodesLength(),
	    		froms, tos, 0, 0, 0);
	    	
	    	double[][] xys = normalizedXYs.toArray();
	    	double[] xs = xys[0];
	    	double[] ys = xys[1];
	    	
	    	int halfTextHeight = fontMetrics.getHeight() / 2;
	    	
	    	int[] lefts = new int[xs.length];
	    	int[] rights = new int[xs.length];
	    	int[] bottoms = new int[ys.length];
	    	int[] tops = new int[ys.length];
	    	
	    	for (int i=0 ; i < nodes.length ; i++)
	    	{
	    		if (nodes[i] != null)
	    		{
		    		int x = graphXOffset + (int)(xs[i] * graphWidth);
		    		int y = graphYOffset + (int)(ys[i] * graphHeight);
		    		int halfTextWidth = fontMetrics.stringWidth(
		    			((Proxy)nodes[i]).getName()) / 2;
		    		
		    		lefts[i] = x - halfTextWidth;
		    		rights[i] = x + halfTextWidth;
		    		bottoms[i] = y + halfTextHeight;
		    		tops[i] = y - halfTextHeight;
	    		}
	    	}
	    	
	    	return(new int[][] {tops, lefts, bottoms, rights});
    	}
	    
	    public int[][] toArray(long rowStart, long columnStart,
	    	int width, int height) throws DataInvalidException
    	{
	    	throw new DataInvalidException("Method not implemented!");
    	}
	    
	    public void toArray(int[][] array, int rowOffset, int columnOffset,
	    	long rowStart, long columnStart, int width, int height)
	    	throws DataInvalidException
    	{
	    	throw new DataInvalidException("Method not implemented!");    	
    	}
	    
	    public Map getParameters()
	    {
	    	HashMap map = new HashMap();
	    	
	    	map.put("name", "PixelNodeBoundsFromNormalizedXYs");
	    	map.put("width", new Long(width()));
	    	map.put("height", new Long(height()));
	    	map.put("graph", graph);
	    	map.put("normalizedXYs", normalizedXYs);
	    	map.put("graphXOffset", new Integer(graphXOffset));
	    	map.put("graphYOffset", new Integer(graphYOffset));
	    	map.put("graphWidth", new Integer(graphWidth));
	    	map.put("graphHeight", new Integer(graphHeight));
	    	map.put("fontMetrics", fontMetrics);
	    	
	    	return(map);
	    }
	}
}