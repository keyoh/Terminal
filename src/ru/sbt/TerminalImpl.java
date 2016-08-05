package ru.sbt;


import java.util.InputMismatchException;
import java.util.Scanner;

class TerminalImpl implements Terminal {
    private long block = 0;

    @Override
    public void mainMenu(double money, String pin, boolean pinValid) throws TerminalException, TerminalServerException, PinValidatorException {
        System.out.println("Введите команду: \n 1 - Проверить состояние счета \n 2 - Положить деньги \n 3 - Снять деньги \n 4 - Ввести пин-код \n 0 - Выход ");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        switch (s) {
            case "1":
                if (pinValid) {
                    System.out.println("Баланс: " + money);
                    mainMenu(money, pin, true);
                    break;
                } else {
                    throw new PinValidatorException("Вы не ввели пин-код.");
                }
            case "2":
                if (pinValid) {
                    deposit(money, pin, true);
                    break;
                } else {
                    throw new PinValidatorException("Вы не ввели пин-код.");
                }
            case "3":
                if (pinValid) {
                    cashOut(money, pin, true);
                    break;
                } else {
                    throw new PinValidatorException("Вы не ввели пин-код.");
                }
            case "4":
                pinValidator(money, pin, pinValid);
                break;
            case "0":
                System.out.println("bye");
                break;
            default:
                throw new TerminalException("Неизвестная команда.");
        }
    }

    @Override
    public void pinValidator(double money, String pin, boolean pinValid) throws TerminalException, TerminalServerException, PinValidatorException {
        if (System.currentTimeMillis() < block + 5000) {
            throw new PinValidatorException("Вы не подождали 5 сек.");
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите пин-код: ");
            String p = scanner.nextLine();
            if (p.equals(pin)) {
                System.out.println("Верно");
                mainMenu(money, pin, true);
            } else {
                System.out.println("Неверный пин-код, попробуйте еще раз (Осталось 2 попытки): ");
                p = scanner.nextLine();
                if (p.equals(pin)) {
                    System.out.println("Верно");
                    mainMenu(money, pin, true);
                } else {
                    System.out.println("Неверный пин-код, попробуйте еще раз (Осталось 1 попытка): ");
                    p = scanner.nextLine();
                    if (p.equals(pin)) {
                        System.out.println("Верно");
                        mainMenu(money, pin, true);
                    } else {
                        System.out.println("Неверный пин-код, ждите 5 сек.");
                        block = System.currentTimeMillis();
                        mainMenu(money, pin, false);
                    }
                }
            }
        }
    }

    @Override
    public void deposit(double money, String pin, boolean pinValid) throws TerminalException, TerminalServerException, PinValidatorException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите сумму (не выше 300 000): ");
        try {
            double m = scanner.nextDouble();
            if (m < 0) {
                throw new TerminalException("Cумма не может быть отрицательной!");
            } else {
                if (m % 100 == 0) {
                    if (m > 300000) {
                        throw new TerminalException("Слишком много!");
                    } else {
                        money = money + m;
                        System.out.println("Баланс: " + money);
                        mainMenu(money, pin, true);
                    }
                } else {
                    throw new TerminalException("Сумма должна быть кратной 100!");
                }

            }
        } catch (InputMismatchException e) {
            throw new TerminalException("Ошибка ввода. Введите сумму числами.");
        }
    }

    @Override
    public void cashOut(double money, String pin, boolean pinValid) throws TerminalException, TerminalServerException, PinValidatorException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите сумму: ");
        try {
            double m = scanner.nextDouble();
            if (m < 0) {
                throw new TerminalException("Cумма не может быть отрицательной!");
            } else {
                if (money < m) {
                    throw new TerminalServerException("Недостаточно средств!");
                } else {
                    if (m % 100 == 0) {
                        money = money - m;
                        System.out.println("Баланс: " + money);
                        mainMenu(money, pin, true);
                    } else {
                        throw new TerminalException("Сумма должна быть кратной 100!");
                    }
                }
            }
        } catch (InputMismatchException e) {
            throw new TerminalException("Ошибка ввода. Введите сумму числами.");
        }
    }
}