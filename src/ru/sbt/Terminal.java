package ru.sbt;

public interface Terminal {
    void mainMenu(double money, String pin, boolean pinValid) throws TerminalException, TerminalServerException, PinValidatorException;

    void deposit (double money, String pin, boolean pinValid) throws TerminalException, TerminalServerException, PinValidatorException;

    void cashOut (double money, String pin, boolean pinValid) throws TerminalException, TerminalServerException, PinValidatorException;

    void pinValidator (double money, String pin, boolean pinValid) throws TerminalException, TerminalServerException, PinValidatorException;

}