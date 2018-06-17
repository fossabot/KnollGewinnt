package underConstruction;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.JobImpressionsCompleted;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sun.prism.Graphics;

import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class imageClass extends JFrame{

	
	public imageClass() throws MalformedURLException{
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Knoll Gewinnt Ver.0.1");
		this.setLayout(new FlowLayout());
		setSize(400, 400);
		//URL resource1 = new URL(imageClass.class.getResource("/images/knoll.png"));
		//URL resource1 = imageClass.class.getResource("/images/knoll.png");

			
		
		
		Icon b = new ImageIcon(getClass().getResource("knolll.png"));
		JButton c = new JButton(b);
		
		JLabel a = new JLabel("GEEKS", b, JLabel.CENTER);
		this.add(a);
		this.add(c);
		this.setVisible(true);
		pack();
		
	}

	public static void main(String[] args) throws MalformedURLException {
		new imageClass();
	}
	

	
	
}
