import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

class pa01 
{
  public static void main(String[] args) 
  {
    String text = "";
    String key = "";
    String cipher = "";

    File keyFile = new File(args[0]); //first argument that takes in key file and appends text to string
    try 
    {
      BufferedReader br = new BufferedReader(new FileReader(keyFile));
      StringBuilder strBuild = new StringBuilder();

      String line;
      while ((line = br.readLine()) != null)
      {
        strBuild.append(line);
      }

      strBuild.setLength(512);
      key = strBuild.toString();

      br.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    File textFile = new File(args[1]); //second argument for plaintext and appends text
    try 
    {
      BufferedReader br = new BufferedReader(new FileReader(textFile));
      StringBuilder strBuild = new StringBuilder();
      strBuild.setLength(512);

      String line;
      while ((line = br.readLine()) != null)
      {
        strBuild.append(line);
      }

      for (int i=0; i < strBuild.length(); i++)
      {
        char padString = strBuild.charAt(i);
        if (padString == 0)
        {
          strBuild.append("x");
        }
      }

      text = strBuild.toString();

      br.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    

    cipher = vigeEnc(key, text);

    cipher = cipher.replaceAll(".{80}", "$0\n");
    System.out.println("\nCiphertext: \n");
    System.out.println(cipher);
  }

  static String vigeEnc(String key, String text)
  {

    int i = 0;
    int j = 0;
    text = text.toLowerCase();
    key = key.toLowerCase();
    String newKey = "";
    String filterText = "";
    String prntText = "";

    for (i=0; i < key.length(); i++)
    {
      char keyFilter = key.charAt(i);
      if(keyFilter < 'a' || keyFilter > 'z')
      {
        continue;
      }
      newKey += (char)(keyFilter); //this is just so that I can print it out after it has lowercased and removed non-letters.
    }

    newKey = newKey.replaceAll(".{80}", "$0\n");
    System.out.println("\nKey: \n");
    System.out.println(newKey);

    //Loop will filter out non-letters and encrypt the characters
    for (i = 0, j = 0; i < text.length(); i++)
    {
      char textFilter = text.charAt(i);

      if (textFilter < 'a' || textFilter > 'z')
      {
        continue;
      }
      prntText += (char)(textFilter);
      filterText += (char)((textFilter + newKey.charAt(j) - 2 * 'a') % 26 + 'a');
      j = ++j % newKey.length();
    }

    prntText = prntText.replaceAll(".{80}", "$0\n");
    System.out.println("\nPlaintext: \n");
    System.out.println(prntText);

    return filterText;
  }


}