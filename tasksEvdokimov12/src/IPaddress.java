public class IPaddress {

    private static boolean checkAddress(String address) {
        if (address.length() <7 || address.length() > 15)      //if length doesn't match the range
            return false;
        String[] numbers = address.split("\\.");
        if (numbers.length != 4)                                //if string doesn't have 4 octets
            return false;
        for (int i = 0; i < 4; i++) {
            try {
                int nowInt = Integer.parseInt(numbers[i]);      //convert to number
                if (nowInt < 0 || nowInt > 255)                 //and if this number doesn't match the range [0-255]
                    return false;
            } catch (NumberFormatException e) {
                return false;                                   //if it isn't a number
            }
        }
        return true;
    }

    public static Long convertIPtoLong(String address) {
        if (!checkAddress(address))         //check if string isn't IP address
            throw new IllegalArgumentException("This string isn't IP address");

        String[] numbersOfAddress = address.split("\\.");       //divide the string according to octets

        long result = 0;
        for (int i = 3; i >= 0; i--) {

            long ip = Long.parseLong(numbersOfAddress[3 - i]);      //take a number from left to right
            long shiftToLeft = ip << (i * 8);       //shift the bits of the number to the position in the IP-address
            result = result | shiftToLeft;          //take this bits to result
        }

        return result;
    }

    public static String convertLongToIP(long number) {
        if (number < 0 || Long.compare(number,4294967295L) > 0)
            throw new IllegalArgumentException("The number doesn't match the range");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 4; i++) {

            result.insert(0, (number & 0xff));          //insert the last 8 bits from right to left
            if (i < 3) {
                result.insert(0,'.');               //insert . between 8 bits
            }
            number = number >> 8;                           //shift the 8 bits (reducing the number)
        }
        return result.toString();
    }
}
