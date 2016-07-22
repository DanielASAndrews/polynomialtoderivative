/**
 * Created by danielandrews on 2016-07-22.
 */

import java.util.*;
import java.lang.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PolynomialToDerivative {
    public static void main (String[] args) throws java.lang.Exception
    {
        String testString = "xyz";
        String derivative = calculateDerivative(testString);

        System.out.println("Derivative is: " + derivative);
    }

    public static String calculateDerivative(String polynomial){

        String[] polynomials = polynomial.split(" ");

        StringBuilder derivative = new StringBuilder();

        for(int i=0; i< polynomials.length; i++){
            System.out.println("Token: " + polynomials[i]);

            boolean isPlusorMinus = polynomials[i].equals("+") || polynomials[i].equals("-");

            if(!isPlusorMinus){

                List <String> polynomialTokens = getPolynomialTokens(polynomials[i]);

                System.out.println(Arrays.toString(polynomialTokens.toArray()));

                Integer coefficient = 0;
                Integer exponent = 0;

                //poly of type: ax^-b
                if(polynomialTokens.size() == 5){

                    coefficient = Integer.parseInt(polynomialTokens.get(0));
                    exponent = Integer.parseInt(polynomialTokens.get(4));
                    coefficient = coefficient*exponent;
                    exponent += 1;
                    polynomialTokens.set(4, exponent.toString());
                    polynomialTokens.set(0, coefficient.toString());
                    derivative = deletePreviousCharacter(derivative);
                    derivative.append("-");

                }
                //poly of type: ax^b
                else if(polynomialTokens.size() > 2){

                    coefficient = Integer.parseInt(polynomialTokens.get(0));
                    exponent = Integer.parseInt(polynomialTokens.get(3));
                    coefficient = coefficient*exponent;
                    exponent -= 1;
                    if (exponent.equals(1)){
                        polynomialTokens.set(2, "");
                        polynomialTokens.set(3, "");
                    }
                    else{
                        polynomialTokens.set(3, exponent.toString());
                    }

                    polynomialTokens.set(0, coefficient.toString());
                }
                //poly of type: ax
                else if (polynomialTokens.size() == 2){
                    exponent = 0;
                    coefficient = Integer.parseInt(polynomialTokens.get(0));
                    polynomialTokens.set(0, coefficient.toString());
                    polynomialTokens.set(1, "");

                }
                //poly of type: a or x
                else if (polynomialTokens.size() == 1){

                    if(isParsableToInt(polynomialTokens.get(0))){
                        coefficient = 0;
                        polynomialTokens.set(0, "");

                        derivative = deletePreviousCharacter(derivative);
                    }
                    else{
                        coefficient = 1;
                        polynomialTokens.set(0, coefficient.toString());
                    }

                }

                for(String s : polynomialTokens) {
                    derivative.append(s);
                }

                derivative.append(" ");
            }
            else {
                derivative.append(polynomials[i]);
                derivative.append(" ");
            }
        }

        //delete extraWhitespace before return
        return deletePreviousCharacter(derivative).toString();
    }


    public static List<String> getPolynomialTokens(String str){
        final Pattern alphaChars = Pattern.compile("[0-9]+|[A-Za-z]+|[^.]");

        List<String> tokens = new ArrayList<String>();
        Matcher matcher = alphaChars.matcher(str);
        while (matcher.find()) {
            tokens.add( matcher.group() );
        }

        return tokens;

    }

    public static StringBuilder deletePreviousCharacter(StringBuilder builder){

        //safety check
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }

        return builder;

    }

    public static boolean isParsableToInt(String input){
        boolean parsable = true;
        try{
            Integer.parseInt(input);
        }catch(NumberFormatException e){
            parsable = false;
        }
        return parsable;
    }

    public static void determinePolynomialType(List <String> polynomialTokens){
    }
}
