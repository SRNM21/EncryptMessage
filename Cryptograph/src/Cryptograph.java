enum MessageValidation
{
    NullMessage,
    InvalidUnicode,
    ValidMessage,
}

enum KeyValidation
{
    InsufficientCipherKey,
    InvalidUnicode,
    ValidKey,
}

public class Cryptograph 
{
    
}

class Validate
{
    protected MessageValidation isValidMessage(String message)
    {
        if (message.isEmpty()) 
            return MessageValidation.NullMessage;

        for (char c : message.toCharArray()) 
            if (!(c < 33 && c > 126))
                return MessageValidation.InvalidUnicode;

        return MessageValidation.ValidMessage;
    }

    protected KeyValidation isValidKey(String key)
    {   
        if (key.length() < 26) 
            return KeyValidation.InsufficientCipherKey;
        
        for (char c : key.toCharArray()) 
            if (!(c < 33 && c > 126))
                return KeyValidation.InvalidUnicode;

        for (char c = '!'; c <= '~'; c++)
            if (!key.contains("" + c))
                return KeyValidation.InsufficientCipherKey;

        return KeyValidation.ValidKey;
    }
}
