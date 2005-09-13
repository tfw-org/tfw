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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.visualizer.graph.Graph;

public class NormalXYDoubleIlmFromGraph
{
	private NormalXYDoubleIlmFromGraph() {}
	
	public static DoubleIlm create(Graph graph)
	{
		Argument.assertNotNull(graph, "graph");
		
		return(new MyDoubleIlm(graph));
	}
	
	private static class MyDoubleIlm implements DoubleIlm, ImmutableProxy
	{
		private final Graph graph;
		
		public MyDoubleIlm(Graph graph)
		{
			this.graph = graph;
		}
		
	    public long width()
	    {
	    	return(graph.nodesLength());
	    }
	    
	    public long height()
	    {
	    	return(2);
	    }

	    public double[][] toArray()
	    	throws DataInvalidException
    	{
//System.out.println("NormalXYDoubleIlmFromGraph:  starting");
	    	Object[] nodes = new Object[(int)graph.nodesLength()];
	    	Object[] froms = new Object[(int)graph.edgesLength()];
	    	Object[] tos = new Object[(int)graph.edgesLength()];
	    	double[] normalXs = new double[(int)graph.nodesLength()];
	    	double[] normalYs = new double[(int)graph.nodesLength()];
	    	int[] nodeClusterNumbers = new int[(int)graph.nodesLength()];
	    	
	    	graph.toArray(nodes, 0, 0, (int)graph.nodesLength(),
	    		froms, tos, 0, 0, (int)graph.edgesLength());
	    	
	    	ArrayList rootNodes = new ArrayList(Arrays.asList(nodes));
	    	
//System.out.println("A: rN.s="+rootNodes.size());
	    	// Produce the set of root nodes.
	    	for (int i=0 ; i < tos.length ; i++)
	    	{
//System.out.println("froms["+i+"]="+froms[i]+" tos["+i+"]="+tos[i]);
	    		if (tos[i] != null)
	    		{
//System.out.println("  removing "+tos[i]);
	    			rootNodes.remove(tos[i]);
	    		}
	    	}
//System.out.println("B: rN.s="+rootNodes.size());
	    	
	    	// If no roots were found, then just pick the first node.
	    	if (rootNodes.size() == 0)
	    	{
	    		rootNodes.add(nodes[0]);
	    	}
	    	
	    	int clusterNumber = 0;
//System.out.println("rootNodes.size() = "+rootNodes.size());
	    	for ( ; rootNodes.size() > 0 ; )
	    	{
	    		if (rootNodes.get(0) == null)
	    		{
	    			rootNodes.remove(0);
	    			continue;
	    		}
//System.out.println("rootNodes.size() = "+rootNodes.size());
		    	// For each root, calculate its normal X's, Y depth and
	    		// node cluster number.
	    		int depth = calculateXandYs(0, clusterNumber, new Object[] {rootNodes.remove(0)},
	    			nodes, froms, tos, normalXs, normalYs, nodeClusterNumbers);
	    		
//System.out.println("depth="+depth);
	    		// Normalize the Y depth.
	    		for (int i=0 ; i < normalYs.length ; i++)
	    		{
	    			if (normalYs[i] >= 1.0)
	    			{
	    				normalYs[i] = normalYs[i] / (depth+1);
	    			}
//System.out.println("new normalYs["+i+"]="+normalYs[i]);
	    		}
	    		clusterNumber++;
	    	}

//for (int i=0 ; i < normalXs.length ; i++)
//System.out.println(i+" nX="+normalXs[i]+" nY="+normalYs[i]);
	    	
	    	int dimension = (int)Math.ceil(Math.sqrt(clusterNumber));
//System.out.println("dimension="+dimension);
	    	
	    	// Position each normalized cluster into a
	    	// "dimension" x "dimension" grid.
	    	for (int i=0 ; i < normalXs.length ; i++)
	    	{
//System.out.println("normalYs["+i+"]="+normalYs[i]+" nCN["+i+"]="+nodeClusterNumbers[i]+" d="+dimension);
	    		normalXs[i] = (normalXs[i] / (double)dimension) +
	    			(double)(nodeClusterNumbers[i] % dimension) / (double)dimension;
	    		normalYs[i] = (normalYs[i] / (double)dimension) +
	    			(double)(nodeClusterNumbers[i] / dimension / (double)dimension); 
	    	}
	    	
//System.out.println("NormalXYDoubleIlmFromGraph:  starting");
	    	return(new double[][] {normalXs, normalYs});
    	}
	    
	    private static int calculateXandYs(int currentLevel, int clusterNumber,
	    	Object[] nodesAtCurrentLevel, Object[] nodes, Object[] froms,
	    	Object[] tos, double[] normalXs, double[] normalYs,
	    	int[] nodeClusterNumbers)
	    {
			ArrayList nodesAtNextLevel = new ArrayList();
			int deepestLevel = currentLevel+1;
			
//System.out.println("Starting node search");
			// Loop through each node at the current level.
			for (int i=0 ; i < nodesAtCurrentLevel.length ; i++)
			{
//System.out.println("current node number "+i);
				// Find the current node in the node list and set its x and y.
				for (int j=0 ; j < nodes.length ; j++)
				{
//System.out.println("node "+j);
					if (nodes[j] != null && nodes[j] == nodesAtCurrentLevel[i])
					{
//System.out.println("Setting X/Y for "+nodes[j]+" i="+i+" nACL.l="+nodesAtCurrentLevel.length);
						normalXs[j] = (double)(i + 1) / (double)(nodesAtCurrentLevel.length + 1);
						normalYs[j] = deepestLevel;
						nodeClusterNumbers[j] = clusterNumber;
//System.out.println("  x["+j+"]="+normalXs[j]+" y["+j+"]="+normalYs[j]+" c["+j+"]="+nodeClusterNumbers[j]);
					}
				}
				
				// Add any children of this node to the next level list and
				// remove that edge from the edge list.
//System.out.println("Starting from search f.l="+froms.length);
//try{
	    		for (int k=0 ; k < froms.length ; k++)
	    		{
//System.out.println("from "+k);
	    			if (froms[k] != null && froms[k] == nodesAtCurrentLevel[i])
	    			{
//System.out.println("froms[k]="+froms[k]);
	    				for (int m=0 ; m < nodes.length ; m++)
	    				{
//System.out.println("froms[k]="+froms[k]+" tos[k]="+tos[k]+" nodes[m]="+nodes[m]+" normalYs["+normalYs[m]);
	    					if (tos[k] == nodes[m] && normalYs[m] == 0.0)
	    					{
//System.out.println("Adding child "+tos[k]);
	    	    				nodesAtNextLevel.add(tos[k]);
	    	    				froms[k] = null;
	    	    				tos[k] = null;   	
	    	    				
	    	    				break;
	    					}
	    				}
	    			}
	    		}
//} catch (Throwable t) {t.printStackTrace(); }
//System.out.println("Finished from search");
			}
//System.out.println("Finished node search");

//System.out.println("nANL.s="+nodesAtNextLevel.size());
			// If there are any nodes in the next level, process them.
			if (nodesAtNextLevel.size() > 0)
			{
//System.out.println("Calling next level");
				deepestLevel = calculateXandYs(deepestLevel, clusterNumber,
					nodesAtNextLevel.toArray(), nodes, froms, tos, normalXs,
					normalYs, nodeClusterNumbers);
			}

    		return(deepestLevel);
	    }
	    
	    public double[][] toArray(long rowStart, long columnStart,
	    	int width, int height) throws DataInvalidException
    	{
	    	double[][] array = toArray();
	    	double[][] returnArray = new double[height][width];
	    	
	    	for (int i=0 ; i < height ; i++)
	    	{
	    		System.arraycopy(array[i+(int)rowStart], (int)columnStart, returnArray[i], 0, width);
	    	}
	    	
	    	return(returnArray);
    	}
	    
	    public void toArray(double[][] array, int rowOffset, int columnOffset,
	    	long rowStart, long columnStart, int width, int height)
    		throws DataInvalidException
		{
	    	throw new DataInvalidException("Method not implemented");    	
		}
	    
	    public Map getParameters()
	    {
	    	HashMap map = new HashMap();
	    	
	    	map.put("name", "NormalXYDoubleIlmFromGraph");
	    	map.put("graph", graph);
	    	map.put("width", new Long(width()));
	    	map.put("height", new Long(height()));
	    	
	    	return(map);
	    }
	}
}