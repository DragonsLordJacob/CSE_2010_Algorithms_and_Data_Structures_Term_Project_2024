/*

  Authors (group members): Vincenzo Barager, Jacob Hall-Burns, Ivan Marriott, Noah Rouse
  Email addresses of group members: vbarager2022@my.fit.edu, jhallburns2021@my.fit.edu, imarriott2023@my.fit.edu, nrouse2019@my.fit.edu
  Group name: TKG

  Course: CSE 2010 Algorithms and Data Structures
  Section: 2-3

  Description of the overall algorithm:
  java -agentpath:./async-profiler-3.0-macos/lib/libasyncProfiler.dylib=start,event=alloc,file=profile.html EvalHangmanPlayer words.txt words.txt
*/

/*
   mapContence is what is stored in fakeHashMap
       get gets the thing stored in mapContence
       put puts itself in the fakeHashMap
    fakeHashMap i a fake hash map
        put puts
        get gets
        shortInt shortans an int string
        stringToInt turns a string in to an int string
        oneCount counts the oness in an int
        stringToDefaltString is like stringToDefaltStringClean but it encodes the length also
        stringToDefaltStringClean turns a string in to an int for real this time
    guessList i a node in a tree of guesses
        addWordLink adds to thee tree
        preProcess does th epre procesing
        calcLetterFrequency reminds the wordLinks to count there letters
    wordLink is a word
        calcLetterFrequency logs what letters are in a word
        selfSort puts itsself in to the guessList
    HangmanPlayer is th required class
   */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class mapContence {
    static int count = 0;
    Object contence;
    //guessList realContence;
    int realIndex;
    //fakeHashMap subMap;
    byte l;
    // constructor for mapContence
    mapContence(int ri, guessList rc, byte c) {
        count++;
        contence = rc;
        //realContence = rc;
        realIndex = ri;
        l = (byte)(c|128);
    }
    // Retrive data from the whole
    public guessList get() {
        if ((l&128) == 0) { // realContence == null
            return ((fakeHashMap)(contence)).get(); //subMap.get()
        }
        if (fakeHashMap.tss == realIndex) {
            return (guessList)(contence); // realContence
        }
        return null;
    }
    // Retrive the data from the desired point
    public guessList get(int ts) {
        if ((l&128) == 0) { // realContence == null
            return ((fakeHashMap)(contence)).get(ts); // subMap.get(ts);
        }
        if (ts == realIndex) {
            return (guessList)(contence); // realContence
        }
        return null;
    }
    // Put data at a desired point in the guessList
    public void put(int ts, guessList v) {
        // If the ts is the same as the realIndex, exit method
        if (ts == realIndex) { 
            return;
        }
        if ((l&128) == 128) { // subMap == null
            contence = new fakeHashMap((byte)(l&127), realIndex, (guessList)(contence));
            // contence.put(realIndex, contence)
            ((fakeHashMap)(contence)).put(ts, v);
            l = (byte)(l&127);
        }
        else {
            ((fakeHashMap)(contence)).put(ts, v); // subMap.put(ts, v);
        }
    }
}
class fakeHashMap {
    static int count = 0;
    /*mapContence[]*/Object map;
    int defaltString;
    byte l;
    guessList zero;
    static int ansSDC = 0;
    static int cSDC = 0;
    static byte i = 0;
    static byte j = 0;
    static int bs = 0;
    static int tss = 0;
    // put method based on a number and a node
    public void put(int ts, guessList v) {
        if (ts == 0) {
            zero = v;
        }
        i = 0;
        while (ts > 0){
            if ((ts&1)==1) { 
                if (l==1){
                    map = v;
                    return;
                }
                if (map == null){
                    map = new mapContence[l];
                }
                if (((mapContence[])map)[i] == null) {
                    ((mapContence[])map)[i] = new mapContence(ts>>1, v, (byte)(l-i-1));
                    break;
                }
                else {
                    ((mapContence[])map)[i].put(ts>>1, v);
                }
                return;
            }
            ts = ts>>1;
            i++;
        }
    }
    // If the input is a string, it is converted to an int
    public void put(String s, guessList v) {
        put(stringToInt(s), v);
    }
    // get method with no parameters
    public guessList get() {
        //System.out.println("getting (" + ts + ") from (" + defaltString + ") of (" + l + ")");
        if (tss == 0) {
            return zero;
        }
        i = 0;
        while (tss > 0) {
            if ((tss&1)==1) {
                if (l==1){
                    return (guessList)map;
                }
                if (map == null){
                    return null;
                }
                if (((mapContence[])map)[i] == null){
                    return null;
                }
                tss = tss>>1;
                return ((mapContence[])map)[i].get();
            }
            tss = tss>>1;
            i++;
        }
        return null;
    }
    // get method with a parameter
    public guessList get(int ts) {
        // if the parameter is equal to 0, return the variable zero
        if (ts == 0) {
            return zero;
        }
        // Iterates until map[i] is either null or the desired value
        i = 0;
        while (ts > 0) {
            if ((ts&1)==1) {
                if (l==1){
                    return (guessList)map;
                }
                if (map == null){
                    return null;
                }
                if (((mapContence[])map)[i] == null) {
                    return null;
                }
                return ((mapContence[])map)[i].get(ts>>1);
            }
            ts = ts>>1;
            i++;
        }
        return null;
    }
    // If a string is provided, it is turned into an int and get is called
    public guessList get(String ts) {
        return get(stringToInt(ts));
    }
    // Constructor for fakeHashMap with a byte, intm and guessList
    fakeHashMap(byte c,int i, guessList first) {
        count++;
        defaltString = 0;
        l = c;
        //map = new mapContence[c];
        put(i, first);
    }
    // Constructor for fakeHashMap with a byte
    fakeHashMap(byte c) {
        count++;
        defaltString = 0;
        l = c;
        //map = new mapContence[c];
    }
    // Constructor for fakeHashMap with a String
    fakeHashMap(String ds) {
        count++;
        int an = stringToDefaltString(ds);
        defaltString = an&16777215;
        l = (byte)(ds.length() - ((an&520093696)>>24));
        //map = new mapContence[l];     
    }
    // Constructor for fakeHashMap with an int and a byte
    fakeHashMap(int s, byte le) {
        count++;
        defaltString = s;
        l = (byte)(le - oneCount(s));        
        //map = new mapContence[l];     
    }
    // method that iterates u and j until ansSDC is found
    public int shortInt(int bs) {
        ansSDC = 0;
        j = 0;
        i = 0;
        while (bs > 0) {
            if (((defaltString>>i)&1) != 1) {
                ansSDC = ansSDC|((bs&1)<<j);
                j+=1;
            }
            i++;
            bs = bs>>1;
        }
        return ansSDC;
    }
    // Custome method for turning a string into an integer.
    public int stringToInt(String s) {
        bs = stringToDefaltStringClean(s);
        ansSDC = 0;
        j = 0;
        i = 0;
        while (i<s.length()) {
            if (((defaltString>>i)&1) != 1) {
                ansSDC = ansSDC|(((bs>>i)&1)<<j);
                j+=1;
            }
            i++;
        }
        return ansSDC;
    }
    public static byte oneCount(int s) {
        ansSDC = 0;
        while (s > 0) {
            if ((s&1) == 1) {
                ansSDC += 1;
            }
            s = s>>1;
        }
        return (byte)ansSDC;
    }
    public static int stringToDefaltString(String s) {
        ansSDC = 0;
        cSDC = 0;
        for (byte i = 0; i<s.length(); i++) {
            if (s.charAt(i) != ' ') {
                ansSDC = ansSDC | (1<<i);
                cSDC+=1;
            }
        }
        ansSDC = ansSDC|(cSDC<<24);
        return ansSDC;
    }
    public static int stringToDefaltStringClean(String s) {
        ansSDC = 0;
        for (byte i = 0; i<s.length(); i++) {
            if (s.charAt(i) != ' '){
                ansSDC = ansSDC | (1<<i);
            }
        }
        return ansSDC;
    }
}
class guessList {
    static int count = 0;
    static guessList guessListSharedHead = null;
    guessList next = null;
    //can add fail count for optimization
    //String asosheatedString;
    int asosheatedStringInt;
    char mostCommonLetter;
    int mostCommonLetterAmount;
    //int[] letterFrequencys;
    static int[] sharedLetterFrequencys = new int[26];
    wordLink posibleWords;
    //wordLink posibleWordsTail;
    /*HashMap<String, guessList>*/fakeHashMap answerLookup;
    byte failCount;
    guessList(int s, byte l) {
        count++;
        //asosheatedString = as;
        asosheatedStringInt = s;
        mostCommonLetter = ' ';
        mostCommonLetterAmount = 0;
        posibleWords = null;
        //posibleWordsTail = null;
        //letterFrequencys = new int[26];
        answerLookup = new fakeHashMap(s, l);//HashMap<String, guessList>();
        failCount = 0;
    }
    guessList(String as) {
        count++;
        //asosheatedString = as;
        asosheatedStringInt = fakeHashMap.stringToDefaltStringClean(as);
        mostCommonLetter = '$';
        mostCommonLetterAmount = 0;
        posibleWords = null;
        //posibleWordsTail = null;
        //letterFrequencys = new int[26];
        answerLookup = new fakeHashMap(as);//HashMap<String, guessList>();
        failCount = 0;
    }
    public void addWordLink(wordLink toAdd, boolean notFirstAdd) {
        
        if (posibleWords == null) {
            posibleWords = toAdd;
            toAdd.next = null;
            //posibleWordsTail = toAdd;
        }
        else {
            toAdd.next = posibleWords;
            posibleWords = toAdd;
            /*posibleWordsTail.next = toAdd;
            posibleWordsTail = toAdd;*/
        }
        //posibleWordsTail.next = null;
        //System.out.println("adding (" + toAdd.dataStart + ")(" + toAdd.dataLength + ") to " + asosheatedStringInt);

        /*if (notFirstAdd){
            for (int i = 0; i<26; i++){
                sharedLetterFrequencys[i] = 0;
            }
            for (int i = 0; i<26; i++){
                //System.out.println("(" + toAdd.dataStart + ")(" + toAdd.dataLength + ").letterFrequencys = " + toAdd.letterFrequencys + " -" + i + "> " + ((toAdd.letterFrequencys>>i)&1));
                sharedLetterFrequencys[i] += (toAdd.letterFrequencys>>i)&1;
                //System.out.println("letterFrequencys[i] = " + letterFrequencys[i] + " mostCommonLetterAmount = " + mostCommonLetterAmount);
            }
            for (int i = 0; i<26; i++){
                if (sharedLetterFrequencys[i] > mostCommonLetterAmount){
                    mostCommonLetterAmount = sharedLetterFrequencys[i];
                    mostCommonLetter = (char)((i+1)|96);
                }
            }
        }*/
        //System.out.println("addingWordLink " + toAdd.data + " to (" + asosheatedString + ")");
        /*for (int i = 0; i<26; i++){
            //System.out.println("(" + toAdd.dataStart + ")(" + toAdd.dataLength + ").letterFrequencys = " + toAdd.letterFrequencys + " -" + i + "> " + ((toAdd.letterFrequencys>>i)&1));
            letterFrequencys[i] += (toAdd.letterFrequencys>>i)&1;
            //System.out.println("letterFrequencys[i] = " + letterFrequencys[i] + " mostCommonLetterAmount = " + mostCommonLetterAmount);
            if (letterFrequencys[i] > mostCommonLetterAmount){
                mostCommonLetterAmount = letterFrequencys[i];
                mostCommonLetter = (char)((i+1)|96);
            }
        }*/
    }
    public void preProcess(){
        for (int i = 0; i<26; i++) {
            sharedLetterFrequencys[i] = 0;
        }
        wordLink pointer = posibleWords;
        while (pointer != null) {
            //System.out.println("addingWordLink " + toAdd.data + " to (" + asosheatedString + ")");
            for (int i = 0; i<26; i++) {
                //System.out.println("(" + pointer.dataStart + ")(" + pointer.dataLength + ").letterFrequencys = " + pointer.letterFrequencys + " -" + i + "> " + ((pointer.letterFrequencys>>i)&1));
                sharedLetterFrequencys[i] += (pointer.letterFrequencys>>i)&1;
                //System.out.println("letterFrequencys[i] = " + letterFrequencys[i] + " mostCommonLetterAmount = " + mostCommonLetterAmount);
            }
            pointer = pointer.next;
        }
        for (int i = 0; i<26; i++) {
            if (sharedLetterFrequencys[i] > mostCommonLetterAmount) {
                mostCommonLetterAmount = sharedLetterFrequencys[i];
                mostCommonLetter = (char)((i+1)|96);
            }
        }
        //System.out.println("preProcessing (" + asosheatedString + ") on " + mostCommonLetter);
        //return;
        /*if (mostCommonLetterAmount == 0 && asosheatedString.length() == 2){
            System.out.println("uto mostCommonLetterAmount = " + mostCommonLetterAmount);
            int x = 0/0;
        }*/
        //ArrayList<guessList> nextProps = new ArrayList<guessList>();
        guessListSharedHead = this;
        next = null;
        while (posibleWords != null) {
            posibleWords = posibleWords.selfSort(answerLookup, mostCommonLetter, asosheatedStringInt/*, nextProps*/, failCount);
        }
        //posibleWordsTail = null;
        guessList i = this.next;
        
        //for (guessList i:nextProps){
        while (i != null) {
            //System.out.println("(" + asosheatedString + ") is calling " + i.asosheatedString);
            guessList j = i.next;
            i.preProcess();
            i = j;;
        }
    }
    // Determines letter frequency
    public void calcLetterFrequency() {
        /*for (int i = 0; i<26; i++){
            sharedLetterFrequencys[i] = 0;
        }*/
        wordLink pointer = posibleWords;
        while (pointer != null) {
            pointer.calcLetterFrequency();
            //System.out.println("addingWordLink " + toAdd.data + " to (" + asosheatedString + ")");
            /*for (int i = 0; i<26; i++){
                //System.out.println("(" + pointer.dataStart + ")(" + pointer.dataLength + ").letterFrequencys = " + pointer.letterFrequencys + " -" + i + "> " + ((pointer.letterFrequencys>>i)&1));
                sharedLetterFrequencys[i] += (pointer.letterFrequencys>>i)&1;
                //System.out.println("letterFrequencys[i] = " + letterFrequencys[i] + " mostCommonLetterAmount = " + mostCommonLetterAmount);
            }*/
            pointer = pointer.next;
        }
        /*for (int i = 0; i<26; i++){
            if (sharedLetterFrequencys[i] > mostCommonLetterAmount){
                mostCommonLetterAmount = sharedLetterFrequencys[i];
                mostCommonLetter = (char)((i+1)|96);
            }
            if (mostCommonLetterAmount > 0 && sharedLetterFrequencys[i] > 0){
                System.out.println("letterFrequencys[" + i + "] = " + sharedLetterFrequencys[i] + " mostCommonLetterAmount = " + mostCommonLetterAmount);
            }
        }*/
    }
}

class wordLink {
    static int count = 0;
    wordLink next;
    //String data;
    //static String sharedData;
    static char[] sharedData;
    int dataStart;
    byte dataLength;
    int letterFrequencys;
    static guessList nw;
    // Constructor for wordlink
    wordLink(int ds, byte dl) {
        count++;
        dataStart = ds;
        dataLength = dl;
        letterFrequencys = 0;
    }
    // calculates frequency
    public void calcLetterFrequency() {
        byte i = 0;
        while (i<dataLength){
            //letterFrequencys = letterFrequencys | (1 << ((((int)(sharedData.charAt(dataStart+i))) & 31)-1));
            letterFrequencys = letterFrequencys | (1 << ((((int)(sharedData[dataStart+i])) & 31)-1));
            i++;
        }
    }
    // Sorting method for wordlink
    public wordLink selfSort(/*HashMap<String, guessList>*/fakeHashMap sortTo, char buyChar, int oldAssossheatedStringInt/*, ArrayList<guessList> nextProps*/, byte ofc) {
        //remove a one from the right spot
        letterFrequencys = letterFrequencys & (~(1 << ((((int)(buyChar)) & 31)-1)));
        //StringBuilder index = new StringBuilder();
        int index = 0;
        boolean notDone = false;
        boolean extra = true;
        char cc = '#';
        byte i = 0;
        while (i < dataLength) {
            //cc = sharedData.charAt(dataStart+i);//data.charAt(i);
            cc = sharedData[dataStart+i];
            if (((oldAssossheatedStringInt>>i)&1)==1) {
                //index.append(cc);
                index |= 1<<i;
            }
            else if (cc == buyChar) {
                //index.append(cc);
                index |= 1<<i;
                extra = false;
            }
            else {
                //index.append(" ");
                notDone = true;
            }
            i++;
        }
        if (extra && ofc == 5) {
           return next;
        }
        //System.out.println("wording (" + data + ") index=" + index);
        //int realIndex = index;//.toString();
        if (sortTo.get(sortTo.shortInt(index)) == null) {
            //System.out.println("(" + oldAssossheatedString + ") is adding (" + realIndex + ") for " + buyChar + " because of (" + data + ")");
            nw = new guessList(index, dataLength);//data.length());
            nw.failCount = ofc;
            if (extra) {
                nw.failCount += 1;
            }
            if (notDone) {
                guessList.guessListSharedHead.next = nw;
                guessList.guessListSharedHead = nw;
                //nextProps.add(nw);
            }
            //System.out.println("putting (" + data + ") realIndex=" + index);
            sortTo.put(sortTo.shortInt(index), nw);
        }
        wordLink oldNext = next;
        //System.out.println("(" + oldAssossheatedString + ") is putting (" + realIndex + ") for " + buyChar + " because of (" + data + ")");
        
        /*if (sortTo.get(realIndex) == null){
            System.out.println("uto realIndex = (" + realIndex + ") and data = (" + data + ")");
        }*/
        sortTo.get(sortTo.shortInt(index)).addWordLink(this, true);
        return oldNext;
    }
}
// Class for the hangmanPlayer
public class HangmanPlayer {
    guessList[] sizeList;
    guessList curentLoc;
    fakeHashMap map;
    int ansSDC;
    int bs;
    int cSDC;
    int tss;
    int ii;
    int jj;
    boolean cont;

    int cleanupNumber = 0;
    int cleanupNumberRate = 300000;
    // gives information to the hangman players and has it try to guess the word
    public HangmanPlayer(String wordFile) throws IOException {
        // Max word length is 24 characters
        sizeList = new guessList[24];
        String as = "";
        // For each space in the sizeList, if the letter is null, create a new guessList class using (as)
        for (int i = 0; i < sizeList.length; i++) {
            as += " ";
            if (sizeList[i] == null){
                sizeList[i] = new guessList(as);
            }
        }
        // Open scanner and start reading file
        try (BufferedReader reader = new BufferedReader(new FileReader(wordFile))) {
            String line;
            int c = 0;            
            StringBuilder data = new StringBuilder();
            int p = -2;
            // While there is another line to read
            while ((line = reader.readLine()) != null) {
                // If the line length is zero, continue to the next line
                if (line.length() == 0){
                    continue;
                }
                p = line.length()-1;
                sizeList[p].addWordLink(new wordLink(c,(byte)line.length()), false); //line.toLowerCase()));
                data.append(line.toLowerCase());
                c+=line.length();
            }
            wordLink.sharedData = new char[c];
            data.getChars(0, c, wordLink.sharedData, 0);
        }
        for (guessList i:sizeList) {
            i.calcLetterFrequency();
            i.preProcess();
        }
        
    }
    // Method for guessing
    public char guess(String currentWord, boolean isNewWord) {
        if (isNewWord) {
            curentLoc = sizeList[currentWord.length()-1];
        }
        if (curentLoc == null) {
            System.out.println("crash word was (" + currentWord + ")");
        }
        cleanupNumber++;
        if (cleanupNumber >= cleanupNumberRate) {
            cleanupNumber = 0;
            System.gc();
        }
        return curentLoc.mostCommonLetter;
    }
    //  calls to see if the guess was correct and changes the next letter to guess and likely words based on the results
    public void feedback(boolean isCorrectGuess, String currentWord) {
        map = curentLoc.answerLookup;
        ansSDC = 0;
        cSDC = 0;
        ii = 0;
        while (ii<currentWord.length()){
            if (currentWord.charAt(ii) != ' ') {
                ansSDC = ansSDC | (1<<ii);
                cSDC+=1;
            }
            ii++;
        }
        ansSDC = ansSDC|(cSDC<<24);
        bs = ansSDC;
        ansSDC = 0;
        jj = 0;
        ii = 0;
        while (ii<currentWord.length()) {
            if (((map.defaltString>>ii)&1) != 1) {
                ansSDC = ansSDC|(((bs>>ii)&1)<<jj);
                jj+=1;
            }
            ii++;
        }
        tss = ansSDC;
        
        curentLoc = null;
        cont = true;
        while (cont){
            cont = false;
            if (tss == 0) {
                curentLoc = map.zero;
                break;
            }
            ii = 0;
            while (tss > 0) {
                if ((tss&1)==1){
                    if (map.l == 1){
                        curentLoc = ((guessList)(map.map));
                        break;
                    }
                    if (((mapContence[])(map.map))[ii] == null){
                        curentLoc = null;
                        break;
                    }
                    tss = tss>>1;
                    
                    if ((((mapContence[])(map.map))[ii].l&128) == 0) { // map.map[ii].realContence == null
                        map = (fakeHashMap)(((mapContence[])(map.map))[ii].contence);//.subMap);
                        cont = true;
                        break;
                    }
                    if (tss == ((mapContence[])(map.map))[ii].realIndex){
                        curentLoc = (guessList)(((mapContence[])(map.map))[ii].contence); // realContence
                        break;
                    }
                    curentLoc = null;
                    break;
                }
                tss = tss>>1;
                ii++;
            }
        }
    }
    public static void testPlay(String wordList) throws IOException {
        HangmanPlayer player = new HangmanPlayer(wordList);
        boolean firstWord = true;            
        System.out.println("ready");

        Scanner sc = new Scanner (System.in, "US-ASCII");
        String word = null;
        while (sc.hasNextLine()){
            word = sc.nextLine();
            if (!firstWord) {
                player.feedback(true, word);
            }
            System.out.println("guess = (" + player.guess(word, firstWord) + ")");
            firstWord = false;
        }
    }
    public static void testPlay() throws IOException {
        testPlay("words.txt");
    }
    public static void testPlayI() throws IOException {
        testPlay("testIn.txt");
    }
}
