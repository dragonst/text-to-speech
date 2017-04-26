package speechtest.speechandbuffer.component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import no.ntnu.item.arctis.runtime.Block;

public class Component extends Block {
	
	BufferedReader br;
	String sentence = "";
	String notepadText = "";
	String sentenceToSay = "";
	int beginIndex = 0;
	int endIndex = 0;
	
	public String getFirstSentence() {
		return "All sentences are produced right after another.";
	}

	public String getSecondSentence() {
		return "With the buffer, each sentence is nicely handled after the other.";
	}

	public String getThirdSentence() {
		return "We even wait for half a second in between. Cool.";
	}

	public void getNotepadText() {
		try {

			

			br = new BufferedReader(new FileReader(System.getProperty("user.home") + "/Desktop/file.txt"));
			while ((sentence = br.readLine()) != null)
			{
				notepadText += sentence;
			}
			

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public String pronounceSentence() {
		
		if (beginIndex == notepadText.length()) System.exit(0);
		
		int i = beginIndex;
		while (i < notepadText.length())
		{
			endIndex += 1;
			
			if (notepadText.charAt(i) == '.' || notepadText.charAt(i) == '?' || notepadText.charAt(i) == '!' || notepadText.charAt(i) == ':' || notepadText.charAt(i) == ';')
			{
				sentenceToSay =  notepadText.substring(beginIndex, endIndex);
			}			
			i++;
		}
		
		beginIndex = endIndex;			
		return sentenceToSay;
	}
}
