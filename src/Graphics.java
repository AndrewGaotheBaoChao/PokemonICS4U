/**
 * Graphics.java
 * Graphics file containing all ASCII art used in program
 */

public class Graphics extends Misc {

    // Opening Title
    private static String[] title = {
            "                               .::.                           ",
            "                              .;:**'                          ",
            "                              `                               ",
            "  .:XHHHHk.              db.   .;;.     dH  MX                ",
            "oMMMMMMMMMMM       ~MM  dMMP :MMMMMR   MMM  MR      ~MRMN     ",
            "QMMMMMb  'MMX       MMMMMMP !MX' :M~   MMM MMM  .oo. XMMM 'MMM",
            "  `MMMM.  )M> :X!Hk. MMMM   XMM.o'  .  MMMMMMM X?XMMM MMM>!MMP",
            "   'MMMb.dM! XM M'?M MMMMMX.`MMMMMMMM~ MM MMM XM `' MX MMXXMM ",
            "    ~MMMMM~ XMM. .XM XM`'MMMb.~*?**~ .MMX M t MMbooMM XMMMMMP ",
            "     ?MMM>  YMMMMMM! MM   `?MMRb.    `MM   !L'MMMMM XM IMMM   ",
            "      MMMX   'MMMM'  MM       ~%:           !Mh.''' dMI IMMP  ",
            "      'MMM.                                             IMX   ",
            "       ~M!M                                             IM    ",
            "                      Created By Andrew Gao                   ",
            "                           ICS4U 2018                         ",
            "                          Pokemon Arena                       "
    };

    // Win Text
    private static String[] win = {
            " ÛÛÛÛÛ ÛÛÛÛÛ                        ÛÛÛÛÛ   ÛÛÛ   ÛÛÛÛÛ  ÛÛÛ             ÛÛÛ",
            "°°ÛÛÛ °°ÛÛÛ                        °°ÛÛÛ   °ÛÛÛ  °°ÛÛÛ  °°°             °ÛÛÛ",
            " °°ÛÛÛ ÛÛÛ    ÛÛÛÛÛÛ  ÛÛÛÛÛ ÛÛÛÛ    °ÛÛÛ   °ÛÛÛ   °ÛÛÛ  ÛÛÛÛ  ÛÛÛÛÛÛÛÛ  °ÛÛÛ",
            "  °°ÛÛÛÛÛ    ÛÛÛ°°ÛÛÛ°°ÛÛÛ °ÛÛÛ     °ÛÛÛ   °ÛÛÛ   °ÛÛÛ °°ÛÛÛ °°ÛÛÛ°°ÛÛÛ °ÛÛÛ",
            "   °°ÛÛÛ    °ÛÛÛ °ÛÛÛ °ÛÛÛ °ÛÛÛ     °°ÛÛÛ  ÛÛÛÛÛ  ÛÛÛ   °ÛÛÛ  °ÛÛÛ °ÛÛÛ °ÛÛÛ",
            "    °ÛÛÛ    °ÛÛÛ °ÛÛÛ °ÛÛÛ °ÛÛÛ      °°°ÛÛÛÛÛ°ÛÛÛÛÛ°    °ÛÛÛ  °ÛÛÛ °ÛÛÛ °°° ",
            "    ÛÛÛÛÛ   °°ÛÛÛÛÛÛ  °°ÛÛÛÛÛÛÛÛ       °°ÛÛÛ °°ÛÛÛ      ÛÛÛÛÛ ÛÛÛÛ ÛÛÛÛÛ ÛÛÛ",
            "   °°°°°     °°°°°°    °°°°°°°°          °°   °°°      °°°°° °°°° °°°°° °°° "
    };

    private static String[] lose = {
            " ÛÛÛÛÛ ÛÛÛÛÛ                        ÛÛÛÛÛ                                 ÛÛÛ",
            "°°ÛÛÛ °°ÛÛÛ                        °°ÛÛÛ                                 °ÛÛÛ",
            " °°ÛÛÛ ÛÛÛ    ÛÛÛÛÛÛ  ÛÛÛÛÛ ÛÛÛÛ    °ÛÛÛ         ÛÛÛÛÛÛ   ÛÛÛÛÛ   ÛÛÛÛÛÛ °ÛÛÛ",
            "  °°ÛÛÛÛÛ    ÛÛÛ°°ÛÛÛ°°ÛÛÛ °ÛÛÛ     °ÛÛÛ        ÛÛÛ°°ÛÛÛ ÛÛÛ°°   ÛÛÛ°°ÛÛÛ°ÛÛÛ",
            "   °°ÛÛÛ    °ÛÛÛ °ÛÛÛ °ÛÛÛ °ÛÛÛ     °ÛÛÛ       °ÛÛÛ °ÛÛÛ°°ÛÛÛÛÛ °ÛÛÛÛÛÛÛ °ÛÛÛ",
            "    °ÛÛÛ    °ÛÛÛ °ÛÛÛ °ÛÛÛ °ÛÛÛ     °ÛÛÛ      Û°ÛÛÛ °ÛÛÛ °°°°ÛÛÛ°ÛÛÛ°°°  °°° ",
            "    ÛÛÛÛÛ   °°ÛÛÛÛÛÛ  °°ÛÛÛÛÛÛÛÛ    ÛÛÛÛÛÛÛÛÛÛÛ°°ÛÛÛÛÛÛ  ÛÛÛÛÛÛ °°ÛÛÛÛÛÛ  ÛÛÛ",
            "   °°°°°     °°°°°°    °°°°°°°°    °°°°°°°°°°°  °°°°°°  °°°°°°   °°°°°°  °°° "
    };

    /**
     * Start title animation
     */
    public static void startTitle(){
        delayedLinePrint(title, 40);
        sleep(1000);
        pause();
    }

    /**
     * Win ASCII display
     */
    public static void win() {
        clearScreen();
        delayedLinePrint(win, 20);
        sleep(1000);
    }

    /**
     * Lose ASCII display
     */
    public static void lose() {
        clearScreen();
        delayedLinePrint(lose, 20);
        sleep(1000);
    }


}
