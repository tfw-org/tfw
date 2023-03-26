package tfw.immutable.ilm.shortilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class ShortIlmConcatenateHorizontal
{
    private ShortIlmConcatenateHorizontal() {}

    public static ShortIlm create(ShortIlm leftIlm, ShortIlm rightIlm)
    {
    	Argument.assertNotNull(leftIlm, "leftIlm");
    	Argument.assertNotNull(rightIlm, "rightIlm");
    	Argument.assertEquals(leftIlm.height(), rightIlm.height(),
    		"leftIlm.height()", "rightIlm.height()");

		return new MyShortIlm(leftIlm, rightIlm);
    }

    private static class MyShortIlm extends AbstractShortIlm
    	implements ImmutableProxy
    {
		private ShortIlm leftIlm;
		private ShortIlm rightIlm;

		MyShortIlm(ShortIlm leftIlm, ShortIlm rightIlm)
		{
		    super(leftIlm.width() + rightIlm.width(), leftIlm.height());
		    
		    this.leftIlm = leftIlm;
		    this.rightIlm = rightIlm;
		}
		
		protected void toArrayImpl(short[][] array, int rowOffset,
			int columnOffset, long rowStart, long columnStart,
			int width, int height) throws DataInvalidException
		{
		    if (columnStart + width <= leftIlm.width())
		    {
				leftIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart, width, height);
		    }
		    else if (columnStart >= leftIlm.width())
			{
				rightIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart - leftIlm.width(), width, height);
		    }
		    else
			{
				int firstamount = (int)(leftIlm.width() - columnStart);
				leftIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart, firstamount, height);
				rightIlm.toArray(array, rowOffset, columnOffset + firstamount,
					rowStart, 0, width - firstamount, height);
	    	}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "ShortIlmConcatenateHorizontal");
			map.put("leftIlm", getImmutableInfo(leftIlm));
			map.put("rightIlm", getImmutableInfo(rightIlm));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}