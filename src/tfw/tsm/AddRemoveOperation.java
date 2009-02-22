package tfw.tsm;

public class AddRemoveOperation
{
	private final Operation operation;
	private final TreeComponent treeComponent;
	
	public static enum Operation
	{
		ADD, REMOVE
	}
	
	public AddRemoveOperation(Operation operation, BranchBox child)
	{
		this.operation = operation;
		this.treeComponent = child.getBranch();
	}
	
	public AddRemoveOperation(Operation operation, TreeComponent child)
	{
		this.operation = operation;
		this.treeComponent = child;
	}
	
	public Operation getOperation()
	{
		return(operation);
	}
	
	public TreeComponent getTreeComponent()
	{
		return(treeComponent);
	}
}
