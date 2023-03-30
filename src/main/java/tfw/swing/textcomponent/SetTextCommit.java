package tfw.swing.textcomponent;

import java.awt.EventQueue;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

public class SetTextCommit extends Commit
{
	private final StringECD textECD;
	private final JTextComponent textComponent;
	private final DocumentListener documentListener;
	
	public SetTextCommit(String name, StringECD textECD,
		JTextComponent textComponent, DocumentListener documentListener,
		Initiator[] initiators)
	{
		super("SetTextCommit["+name+"]",
			new ObjectECD[] {textECD},
			null,
			initiators);
		
		if (textComponent == null)
			throw new IllegalArgumentException(
				"textComponent == null not allowed!");
		
		this.textECD = textECD;
		this.textComponent = textComponent;
		this.documentListener = documentListener;
	}
	
	protected void commit()
	{
		final String text = (String)get(textECD);
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				if (documentListener != null)
				{
					textComponent.getDocument().removeDocumentListener(
						documentListener);
				}

				textComponent.setText(text);

				if (documentListener != null)
				{
					textComponent.getDocument().addDocumentListener(
						documentListener);
				}				
			}
		});
	}
}