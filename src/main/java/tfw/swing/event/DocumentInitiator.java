package tfw.swing.event;

import	javax.swing.event.DocumentEvent;
import	javax.swing.event.DocumentListener;
import	javax.swing.text.BadLocationException;
import	tfw.tsm.Initiator;
import	tfw.tsm.ecd.ObjectECD;
import	tfw.tsm.ecd.StringECD;

public class DocumentInitiator extends Initiator implements DocumentListener
{
	private final StringECD textECD;
	
	public DocumentInitiator(String name, StringECD textECD)
	{
		super("DocumentInitiator["+name+"]",
			new ObjectECD[] {textECD});
		
		this.textECD = textECD;
	}
	
	public void changedUpdate(DocumentEvent e)
	{
		try
		{
			set(textECD,
				e.getDocument().getText(0, e.getDocument().getLength()));
		}
		catch(BadLocationException ble) {}
	}
	
	public void insertUpdate(DocumentEvent e)
	{
		try
		{
			set(textECD,
				e.getDocument().getText(0, e.getDocument().getLength()));
		}
		catch(BadLocationException ble) {}
	}
	
	public void removeUpdate(DocumentEvent e)
	{
		try
		{
			set(textECD,
				e.getDocument().getText(0, e.getDocument().getLength()));
		}
		catch(BadLocationException ble) {}
	}
}
