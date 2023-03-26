package tfw.immutable.ilm.floatilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class FloatIlmConcatenateHorizontal
{
    private FloatIlmConcatenateHorizontal() {}

    public static FloatIlm create(FloatIlm leftIlm, FloatIlm rightIlm)
    {
    	Argument.assertNotNull(leftIlm, "leftIlm");
    	Argument.assertNotNull(rightIlm, "rightIlm");
    	Argument.assertEquals(leftIlm.height(), rightIlm.height(),
    		"leftIlm.height()", "rightIlm.height()");

		return new MyFloatIlm(leftIlm, rightIlm);
    }

    private static class MyFloatIlm extends AbstractFloatIlm
    	implements ImmutableProxy
    {
		private FloatIlm leftIlm;
		private FloatIlm rightIlm;

		MyFloatIlm(FloatIlm leftIlm, FloatIlm rightIlm)
		{
		    super(leftIlm.width() + rightIlm.width(), leftIlm.height());
		    
		    this.leftIlm = leftIlm;
		    this.rightIlm = rightIlm;
		}
		
		protected void toArrayImpl(float[][] array, int rowOffset,
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
			
			map.put("name", "FloatIlmConcatenateHorizontal");
			map.put("leftIlm", getImmutableInfo(leftIlm));
			map.put("rightIlm", getImmutableInfo(rightIlm));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}