import java.util.*;
import java.util.regex.*;
import java.io.File;

public class SpellCheck{

	static ArrayList<String> editDistance(String word){
		 	ArrayList<String> s=new ArrayList<String>();
		 	int n=word.length();
		 	int i=0;
		 	//Deletes
		 	for(i=0;i<n;++i){
		 		s.add(word.substring(0,i)+word.substring(i+1,n));
		 	}
		 	//Insert
		 	for(i=0;i<=n;++i){
		 		for(char ch='a';ch<='z';++ch){
		 			s.add(word.substring(0,i)+ch+word.substring(i,n));
		 		}
		 	}
		 	//Transpose Adjacent
		 	for(i=0;i<n-1;++i){				   // charAt(i+1)          charAt(i)
		 		s.add(word.substring(0,i)+word.substring(i+1,i+2)+word.substring(i,i+1)+word.substring(i+2,n));
		 	}
		 	//Substitution
		 	for(i=0;i<n;++i){
		 		for(char ch='a';ch<='z';++ch){                              //012345
		 			s.add(word.substring(0,i)+ch+word.substring(i+1,n));    //aginst
		 		}
		 	}
		 	//System.out.println(s);
		 	return s;
	}

	public static void main(String[] args){
		//For storing corpus
		String content=null;

		//For lookup table of words
		HashMap<String,Integer> dict=new HashMap<String,Integer>();

		//Reading a corpus into a string
		try{
			content = new Scanner(new File("big_doc.txt")).useDelimiter("\\Z").next();
		}
		catch(Exception e){
        	e.printStackTrace();
      	}


      	//Extract words put in the lookup table
      	Pattern pattern=Pattern.compile("\\w+"); 
       	Matcher matcher=pattern.matcher(content);

        while (matcher.find()) {
        	String s=matcher.group();
            if(dict.containsKey(s))
            	dict.put(s,dict.get(s)+1);
            else
            	dict.put(s,1);
        }

        //Taking Input From User
        System.out.print("Enter word :");  
        Scanner in=new Scanner(System.in);
        String word=in.nextLine();
        
        //Collect possible words
        ArrayList<String> possibleWords=SpellCheck.editDistance(word);
        String requiredWord=null;

		//Collect distance two words
		ArrayList<String> x=new ArrayList<String>();
        for(String pword:possibleWords){
        	x.addAll(SpellCheck.editDistance(pword));
        }
        possibleWords.addAll(x);

        //Find the word
        int val=0,wordVal=0;
        for(String pword:possibleWords){
        	val=(dict.get(pword)!=null)?dict.get(pword):0;
        	if(val>wordVal){
        		wordVal=val;
        		requiredWord=pword;
        	}
        }

        if(wordVal==0){
        	//If we doesn't get anything matching to word in your corpus
        	//Print the enter word itself
        	System.out.println("Correct word : "+word);
        }
        else{
        	System.out.println("Correct word : "+requiredWord);
        }
	}

}