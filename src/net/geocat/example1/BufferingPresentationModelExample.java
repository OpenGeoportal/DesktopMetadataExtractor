package net.geocat.example1;

/*
 Code revised from Desktop Java Live:
 http://www.sourcebeat.com/downloads/
 */
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.value.Trigger;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class BufferingPresentationModelExample extends JPanel {

	private static final long serialVersionUID = -4791067419554778174L;
	private PersonBean personBean;
	private Trigger trigger;
	private PresentationModel<PersonBean> presentationModel;

	public BufferingPresentationModelExample() {
		DefaultFormBuilder defaultFormBuilder = new DefaultFormBuilder(
				new FormLayout("p, 2dlu, p:g"));
		defaultFormBuilder.setDefaultDialogBorder();

		this.personBean = new PersonBean("Scott", "Delap");

		this.trigger = new Trigger();
		this.presentationModel = new PresentationModel<PersonBean>(
				this.personBean, this.trigger);
		ValueModel firstNameAdapter = presentationModel
				.getBufferedModel("firstName");
		ValueModel lastNameAdapter = presentationModel
				.getBufferedModel("lastName");

		JTextField firstNameTextField = BasicComponentFactory
				.createTextField(firstNameAdapter);
		JTextField lastNameTextField = BasicComponentFactory
				.createTextField(lastNameAdapter);

		defaultFormBuilder.append("First Name: ", firstNameTextField);
		defaultFormBuilder.append("Last Name: ", lastNameTextField);
		defaultFormBuilder.append(new JButton(new FlushBufferAction()), 3);
		defaultFormBuilder.append(new JButton(new CommitBufferAction()), 3);
		defaultFormBuilder.append(
				new JButton(new ShowValueHolderValueAction()), 3);

		add(defaultFormBuilder.getPanel());
	}

	private class CommitBufferAction extends AbstractAction {
		private static final long serialVersionUID = -2319919682773161492L;

		public CommitBufferAction() {
			super("Commit Buffer");
		}

		public void actionPerformed(ActionEvent event) {
			trigger.triggerCommit();
		}
	}

	private class FlushBufferAction extends AbstractAction {

		private static final long serialVersionUID = 5896507253397474557L;

		public FlushBufferAction() {
			super("Flush Buffer");
		}

		public void actionPerformed(ActionEvent event) {
			trigger.triggerFlush();
		}
	}

	private class ShowValueHolderValueAction extends AbstractAction {

		private static final long serialVersionUID = -1619993035028074566L;

		public ShowValueHolderValueAction() {
			super("Show Value");
		}

		public void actionPerformed(ActionEvent event) {
			StringBuffer message = new StringBuffer();
			message.append("<html>");
			message.append("<b>First Name:</b> ");
			message.append(personBean.getFirstName());
			message.append("<br><b>Last Name:</b> ");
			message.append(personBean.getLastName());
			message.append("<br><b>Is Buffering:</b> ");
			message.append(presentationModel.isBuffering());
			message.append("</html>");

			JOptionPane.showMessageDialog(null, message.toString());
		}
	}

	public class PersonBean extends Model {

		private static final long serialVersionUID = 1379721450026567546L;
		private String firstName;
		private String lastName;

		public static final String FIRST_NAME_PROPERTY = "firstName";
		public static final String LAST_NAME_PROPERTY = "lastName";

		public PersonBean(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			String oldValue = this.firstName;
			this.firstName = firstName;
			firePropertyChange(FIRST_NAME_PROPERTY, oldValue, this.firstName);
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			String oldValue = this.lastName;
			this.lastName = lastName;
			firePropertyChange(LAST_NAME_PROPERTY, oldValue, this.lastName);
		}
	}

	public static void main(String[] a) {
		JFrame f = new JFrame("Buffering Presentation Model Example");
		f.setDefaultCloseOperation(2);
		f.add(new BufferingPresentationModelExample());
		f.pack();
		f.setVisible(true);
	}
}