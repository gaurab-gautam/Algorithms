/**
 *
 * @author Gaurab R. Gautam
 */
package utility;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;


public class ConsoleProgressBar
{
    // class for constants
    private static final class ANSI_CONSTANTS
    {
        // ANSI_CONSTANTS color constants
        private static final String RESET = "\033[0m";
        private static final String BLUE = "\033[34m";
        private static final String WHITE = "\033[37m";
        private static final String BLACK = "\033[30m";

        
        // ANSI_CONSTANTS background constants
        private static final String BRIGHT_WHITE_BG = "\033[47;1m";
        private static final String BRIGHT_WHITE = "\033[37;1m";
        private static final String BRIGHT_BLACK = "\033[30;1m";
        
        
        // ANSI_CONSTANTS constant to clear line
        private static final String CLEAR_CONSOLE_LINE = "\033[2K";
        private static final String BACKSPACE = "\b";

        // cursor movements
        private static final String CURSOR_UP = "\033[%dA";
        private static final String CURSOR_DOWN = "\033[%dB";
        private static final String CURSOR_RIGHT = "\033[%dC";
        private static final String CURSOR_LEFT = "\033[%dD";

        // blocks for percent progress bar
        private static final String HALF_BLOCK = "\u275A";    //medium HALF_BLOCK 
        private static final String FULL_BLOCK = "\u2588";
        private static final String LIGHT_BLOCK = "\u2591";
        private static final String LEFT_BLOCK = "\u258C";
        
        private static final String BACKSLASH = "\\";
        private static final String FORWARD_SLASH = "/";
        private static final String VERTICAL_LINE = "|";
        private static final String HORIZONTAL_LINE = "――";
        private static final String AMPERSAND = "#";
        private static final String DOT = ".";
        private static final String ASTERISK = "*";
    }
    
    // Enumerator for indicator choice
    public static enum ProgressBarIndicator 
    {
        AMPERSAND(ANSI_CONSTANTS.AMPERSAND),
        ASTERISK(ANSI_CONSTANTS.ASTERISK),
        DOT(ANSI_CONSTANTS.DOT),
        HALF_BLOCK(ANSI_CONSTANTS.HALF_BLOCK),
        LEFT_BLOCK(ANSI_CONSTANTS.LEFT_BLOCK),
        FULL_BLOCK(ANSI_CONSTANTS.FULL_BLOCK);  
        
        private final String name;

        ProgressBarIndicator(String bar)
        {
            name = bar;
        }

        @Override
        public String toString()
        {
            return name;
        }
    };
    
    // Enumerator for available progress bar styles
    public static enum ProgressBarStyle 
    {
        DETERMINATE("Step"),                    // Shows progress in percentage
        INDETERMINATE("Indefinite"),            // basic style - progress in steps per round (repetative)
        MARQUEE("Marquee"),                     // Windows marquee style
        CIRCULAR("Circular"),                   // Circular animation
        FADING_CIRCULAR("Fading Circular"),     // Circular animation with fading style
        SPINNING_BAR("Spinning Bar");           // Old spinning bar
        
        private final String name;

        ProgressBarStyle(String bar)
        {
            name = bar;
        }

        @Override
        public String toString()
        {
            return name;
        }
    };
    
    // Timer for indeterminate progress bar
    private Timer timer;
    
    // indefinite ProgressBar run
    private IndefiniteProgressBar indefiniteProgressBar;
    
    // percent progress variables
    private int STEP_SIZE = 0;
    private int STEP_INCREMENT = 0;
    private final int MAX_STEPS = 100;
    private int percentDone = 0;
    private final int SIZE;
    private final boolean hybridBar;
    
    // default values
    private String header = "Processing"; 
    private ProgressBarStyle progressBarStyle = ProgressBarStyle.INDETERMINATE;  
    private ProgressBarIndicator progressBarIndicator = ProgressBarIndicator.AMPERSAND;
    private final String PROGRESS_BAR_INDICATOR_COLOR = ANSI_CONSTANTS.BLUE;
    
    
    // constructor for determinate progress bar
    public ConsoleProgressBar(int size) 
    {
        this.hybridBar = true;
        SIZE = size;
        this.progressBarStyle = ProgressBarStyle.DETERMINATE;
    }
    
    // constructor for indeterminate progress bar
    public ConsoleProgressBar() 
    {
        this.hybridBar = false;
        SIZE = Integer.MAX_VALUE;  // size unknown
        STEP_SIZE = 0;
        this.progressBarStyle = ProgressBarStyle.INDETERMINATE;
    }
    
    public ProgressBarStyle getProgressBarStyle()
    {
        return this.progressBarStyle;
    }
    
    //
    // some setters
    //
    public void setIndeterminateProgressBarStyle(ProgressBarStyle style)
    {
        // only allow to set style of indeterminate progress bar as either:
        // 1. basic indeterminate (INDETERMINATE); or:
        // 2. marquee style (MARQUEE); or
        // 3. circular ("CIRCULAR")
        // 4. fading circular(FADING_CIRCULAR)
        // 5. spinning bar (SPINNING_BAR)
        if (this.progressBarStyle != ProgressBarStyle.DETERMINATE)
        {
            this.progressBarStyle = style;
        }
    }
    
    public void setProgressBarIndicator(ProgressBarIndicator indicator)
    {
        this.progressBarIndicator = indicator;
    }
    
    public void setHeader(String val)
    {
        this.header = val;
    }
    
    // builds determinate progress bar
    private void buildDeterminateProgressBar(String style)
    {
        // if progress percent is zero, display marquee style
        if (this.percentDone == 0)
        {
            if (this.timer == null)
            {
                this.progressBarStyle = ProgressBarStyle.MARQUEE;

                // create indefiniteProgressBar object for timer
                this.indefiniteProgressBar = new IndefiniteProgressBar();

                long interval = 50;     // interval for marquee style
                long delay = 0;

                // create timer object
                this.timer = new Timer();

                // do progress bar animation on provided interval
                this.timer.schedule(this.indefiniteProgressBar, delay, interval); 
            }
            
            return;
        }
        else
        {
            if (timer != null)
            {
                // reset 
                print(ANSI_CONSTANTS.RESET);
                print(ANSI_CONSTANTS.CLEAR_CONSOLE_LINE);
                print("\r" + this.header + ":\n");
                this.progressBarStyle = ProgressBarStyle.DETERMINATE;
                this.timer.cancel();
                this.timer = null;
                this.indefiniteProgressBar = null;
            }
        }
        
        // user chosen or default color
        String bar = PROGRESS_BAR_INDICATOR_COLOR;
        
        // progress bar style is generic keyboard characters
        if (!style.equals(ANSI_CONSTANTS.FULL_BLOCK) && (!style.equals(ANSI_CONSTANTS.HALF_BLOCK)))  // not block
        {
            bar += "[";
        }
        
        // update progress in steps
        for (int i = 0; i < this.MAX_STEPS; i += 2)
        {
            if (i < this.percentDone)
            {
                bar += style;
            }
            else
            {
                // shade remaining job as per user specified or default progress indicator
                switch (style) {
                    case ANSI_CONSTANTS.FULL_BLOCK:
                        bar += ANSI_CONSTANTS.BRIGHT_WHITE_BG + ANSI_CONSTANTS.LIGHT_BLOCK;
                        break;
                        
                    case ANSI_CONSTANTS.HALF_BLOCK:
                        bar += ANSI_CONSTANTS.WHITE + ANSI_CONSTANTS.HALF_BLOCK;
                        break;
                        
                    default:
                        bar += "]";
                        bar += "-";
                        break;
                }
            }
        }
        
        // update text of percent complete
        bar += ANSI_CONSTANTS.RESET + "  [ " + this.percentDone + "% completed! ]";
        
        // print on the same line
        print("\r " + bar);
    }
    
    // update progress for determinate bar
    public void update(int processed)
    {
        // hybrid bar to show initial progress always as inderminate
        // once the progress percent increases from 0, bar switches to determinate style
        if (this.hybridBar)
        {
            // randomy get incremental value from 1 to 5
            STEP_INCREMENT = 1 + (int)Math.ceil(Math.random() * 4);  
            STEP_SIZE = (int)Math.round(SIZE/Math.round(MAX_STEPS/(1.0 * STEP_INCREMENT)));
            
            // when processing work is very small, make adjustments to incorporate progress within 100%
            if (SIZE < MAX_STEPS)
            {
                STEP_INCREMENT = 1;
                STEP_SIZE = 1;
                
                // percent done so far
                this.percentDone = (int)Math.round(processed/(SIZE * 1.0)) * 100;
                
                buildDeterminateProgressBar(this.progressBarIndicator.toString()); 
            }
            
            // step level hasn't reached
            // each progress is made only when certain amount of work is processed
            else if (! ((processed != SIZE) && (processed < ((int)Math.round(this.percentDone/100.0 * SIZE) + STEP_SIZE))) )
            {
                // increment percent done and repaint progress bar
                this.percentDone += STEP_INCREMENT;
                
                // make sure step cannot exceed 100%
                if (this.percentDone >= MAX_STEPS) 
                {
                    this.percentDone = MAX_STEPS;
                }

                buildDeterminateProgressBar(this.progressBarIndicator.toString()); 

                if (this.percentDone == MAX_STEPS) 
                {
                    System.out.println();
                }
            }
        }
    }
    
    // 
    // start progress bar
    //
    public void start()
    {
        // indeterminate progress bar
        if (this.progressBarStyle == ProgressBarStyle.INDETERMINATE ||
                this.progressBarStyle == ProgressBarStyle.MARQUEE ||
                this.progressBarStyle == ProgressBarStyle.CIRCULAR ||
                this.progressBarStyle == ProgressBarStyle.FADING_CIRCULAR ||
                this.progressBarStyle == ProgressBarStyle.SPINNING_BAR)
        {
            if (this.progressBarStyle == ProgressBarStyle.CIRCULAR ||
                this.progressBarStyle == ProgressBarStyle.FADING_CIRCULAR)
            {
                // print header
                print(this.header + ":\n");
            }
            else
            {
                print(this.header + ": ");
            }
            
            // set interval and flag
            long interval = 1000;
            long delay = 0;
            
            if (this.progressBarStyle == ProgressBarStyle.MARQUEE)
            {
                interval = 50;
            }
            else if (progressBarStyle == ProgressBarStyle.CIRCULAR || 
                     (progressBarStyle == ProgressBarStyle.FADING_CIRCULAR))
            {
                interval = 200;
            }
            else if (this.progressBarStyle == ProgressBarStyle.SPINNING_BAR)
            {
                interval = 100;
            }
            
            // create indefiniteProgressBar object for timer
            this.indefiniteProgressBar = new IndefiniteProgressBar();
            
            // create timer object
            this.timer = new Timer();
            
            // do progress bar animation on provided interval
            this.timer.schedule(this.indefiniteProgressBar, delay, interval); 
        }
        
        // determinate progress bar
        else {
            buildDeterminateProgressBar(this.progressBarIndicator.toString());      
        }
    }
    
    // stop animation for indeterminate progress bar
    public void stop()
    {
        if (this.progressBarStyle == ProgressBarStyle.INDETERMINATE ||
                this.progressBarStyle == ProgressBarStyle.MARQUEE ||
                this.progressBarStyle == ProgressBarStyle.CIRCULAR ||
                this.progressBarStyle == ProgressBarStyle.FADING_CIRCULAR ||
                this.progressBarStyle == ProgressBarStyle.SPINNING_BAR)
        {
            // reset 
            print(ANSI_CONSTANTS.RESET);
            this.timer.cancel();
        }
    }
    
    private void print(String text)
    {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(java.io.FileDescriptor.out), "UTF-8"), 512);
            out.write(text);
            out.flush();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    // inner class for timer object
    class IndefiniteProgressBar extends TimerTask 
    {
        // class member variable declarations
        final int MAX_STEPS;    // total possible steps for each round
        int numProgress;        // decreasing progress indicator
        int cursorIndex = 0;    // current cursor position
        boolean forwardFlag = true; // flag to identify forward/backward movement for marquee bar
        int colorIndex = 0;     // index to choose color for circular animation
        
        
        public IndefiniteProgressBar()
        {
            // assign desired maximum steps
            switch (progressBarStyle) {
                case MARQUEE:
                    this.MAX_STEPS = 20;
                    break;
                    
                case CIRCULAR:
                case FADING_CIRCULAR:
                    this.MAX_STEPS = 8;
                    break;
                
                case SPINNING_BAR:
                    this.MAX_STEPS = 4;
                    break;
                    
                default:
                    this.MAX_STEPS = 5;
                    break;
            }
            
            this.numProgress = this.MAX_STEPS;
            
            // build initial animation for fading circular bar
            if (progressBarStyle == ProgressBarStyle.FADING_CIRCULAR)
            {
                buildFadingCircularBar();
            }
        }
        
        //
        // runs on specified interval
        //
        @Override
        public void run() 
        {
            // if max steps hasn't been reached for each round
            if (this.numProgress > 0) 
            {
                this.numProgress--;
                
                // build bar as per style choosen
                // for fading bar just update animation 
                switch (progressBarStyle) {
                    case MARQUEE:
                        this.cursorIndex = (this.forwardFlag) ? (this.cursorIndex + 1) : (this.cursorIndex - 1);
                        buildMarqueeBar();
                        break;
                        
                    case CIRCULAR:
                        buildCircularBar(this.cursorIndex, ""); // "": no coloring/fading
                        this.cursorIndex += 1;
                        break;
                        
                    case FADING_CIRCULAR:
                        updateFadingCircularBar(this.cursorIndex);
                        this.cursorIndex += 1;
                        break;
                        
                    case SPINNING_BAR:
                        String [] indicators = {ANSI_CONSTANTS.BACKSLASH, ANSI_CONSTANTS.VERTICAL_LINE, 
                                              ANSI_CONSTANTS.FORWARD_SLASH, ANSI_CONSTANTS.HORIZONTAL_LINE};
                        
                        
                        print(ANSI_CONSTANTS.CLEAR_CONSOLE_LINE);
                        print("\r" + header + ": " + 
                                (this.cursorIndex == 3 ? indicators[this.cursorIndex] :
                                        " " + indicators[this.cursorIndex]));
                        this.cursorIndex += 1;
                        break;
                        
                    default:
                        print(PROGRESS_BAR_INDICATOR_COLOR + ANSI_CONSTANTS.LEFT_BLOCK + ANSI_CONSTANTS.RESET);
                        break;
                }
            } 
            
            // max step reached for the round, reset animation
            else 
            {
                numProgress = this.MAX_STEPS;
                
                switch (progressBarStyle) 
                {
                    case MARQUEE:
                        forwardFlag = !forwardFlag;
                        buildMarqueeBar();
                        break;
                    
                    case CIRCULAR:
                        resetCircularBar();
                        break;
                    
                    case FADING_CIRCULAR:
                        this.cursorIndex = 0;
                        this.colorIndex = this.colorIndex == 0 ? 1 : 0;
                        break;
                    
                    case SPINNING_BAR:
                        this.cursorIndex = 0;
                        break;
                        
                    default:
                        print(ANSI_CONSTANTS.CLEAR_CONSOLE_LINE);
                        print("\r" + header + ": ");
                        break;
                }
            }
        }
        
        //
        // build marquee style bar
        //
        private void buildMarqueeBar()
        {
            print(ANSI_CONSTANTS.CLEAR_CONSOLE_LINE); 
            print("\r" + header + ": ");
            
            for (int i = 0; i <= this.MAX_STEPS; i++) {
                // for current position -- highlight
                if (i == this.cursorIndex)
                {
                    print(PROGRESS_BAR_INDICATOR_COLOR + ANSI_CONSTANTS.FULL_BLOCK + ANSI_CONSTANTS.RESET );
                } 
                // for other positions -- unhightlight
                else
                {
                    print(ANSI_CONSTANTS.WHITE + ANSI_CONSTANTS.LIGHT_BLOCK + ANSI_CONSTANTS.RESET );
                }
            }
        }
        
        //
        // reset circular bar for next round
        private void resetCircularBar()
        {
            // clear the first line
            print(ANSI_CONSTANTS.CLEAR_CONSOLE_LINE);

            // move cursor down and clear that line
            moveCursor(ANSI_CONSTANTS.CURSOR_DOWN, 1);
            print(ANSI_CONSTANTS.CLEAR_CONSOLE_LINE);

            // move cursor down to the last line and clear that line
            moveCursor(ANSI_CONSTANTS.CURSOR_DOWN, 1);
            print(ANSI_CONSTANTS.CLEAR_CONSOLE_LINE);
            
            // reset cursor to the first line
            moveCursor(ANSI_CONSTANTS.CURSOR_UP, 2);
            
            this.cursorIndex = 0;
        }
        
        private void buildCircularBar(int index, String color)
        {
            final int DEFAULT_POSITION = header.length() + 8;
            final int LINE_BEGINNING = 500;
            
            //    \|/
            //  ―― O ――
            //    /|\
            String[] indicators = {
                ANSI_CONSTANTS.BACKSLASH, ANSI_CONSTANTS.VERTICAL_LINE, ANSI_CONSTANTS.FORWARD_SLASH, 
                ANSI_CONSTANTS.HORIZONTAL_LINE, ANSI_CONSTANTS.BACKSLASH, ANSI_CONSTANTS.VERTICAL_LINE, 
                ANSI_CONSTANTS.FORWARD_SLASH, ANSI_CONSTANTS.HORIZONTAL_LINE 
            };
            
            
            // index of indicators for circular bar
            // for each case/index: move curor to desired line/location, 
            // clear line if necessary and print animation indicator
            switch (index) {
                case 0:
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);  //move at the beginning of the line
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION - 1);
                    print(color + indicators[0]);
                    moveCursor(ANSI_CONSTANTS.CURSOR_DOWN, 1);    // move cursor one line down
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);  //move at the beginning of the line
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION);
                    print("O");
                    moveCursor(ANSI_CONSTANTS.CURSOR_UP, 1);  
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);  
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION);                 
                    break;
                    
                case 3:
                    moveCursor(ANSI_CONSTANTS.CURSOR_DOWN, 1);
                    print(ANSI_CONSTANTS.CLEAR_CONSOLE_LINE);
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION);
                    print("O ");
                    print(color + indicators[3]);
                    break;
                    
                case 4:
                    moveCursor(ANSI_CONSTANTS.CURSOR_DOWN, 1);
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION + 1);
                    print(color + indicators[4]);
                    break;
                   
                case 5:
                    print(ANSI_CONSTANTS.CLEAR_CONSOLE_LINE);
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION);
                    print(color + indicators[5]);
                    print(color + indicators[4]);
                    break;
                    
                case 6:
                    print(ANSI_CONSTANTS.CLEAR_CONSOLE_LINE);
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION - 1);
                    print(color + indicators[6]);
                    print(color + indicators[5]);
                    print(color + indicators[4]);
                    break;
                    
                case 7:
                    // move cursor back up
                    moveCursor(ANSI_CONSTANTS.CURSOR_UP, 1);
                    print(ANSI_CONSTANTS.CLEAR_CONSOLE_LINE);
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, 100);
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION - 3);
                    print(color + indicators[7]);
                    print(" O ");
                    print(color + indicators[3]);
                    
                    // reset cursor to the first line
                    moveCursor(ANSI_CONSTANTS.CURSOR_UP, 1);
                    break;
                
                // case 1 and 2 -- first line
                default:
                    print(color + indicators[index]);
                    break;
            }
            
        }
        
        //
        // moves cursor to desired line/location (left, right, up, down)
        //
        private void moveCursor(String direction, int unit)
        {
            print(String.format(direction, unit));
        }
        
        // animation for fading circular progress bar
        private void updateFadingCircularBar(int index)
        {
            final int DEFAULT_POSITION = header.length() + 8;
            final int LINE_BEGINNING = 500;
            String [] colors = {ANSI_CONSTANTS.BRIGHT_WHITE, ANSI_CONSTANTS.BRIGHT_BLACK};
            String[] indicators = {
                ANSI_CONSTANTS.BACKSLASH, ANSI_CONSTANTS.VERTICAL_LINE, ANSI_CONSTANTS.FORWARD_SLASH, 
                ANSI_CONSTANTS.HORIZONTAL_LINE, ANSI_CONSTANTS.BACKSLASH, ANSI_CONSTANTS.VERTICAL_LINE, 
                ANSI_CONSTANTS.FORWARD_SLASH, ANSI_CONSTANTS.HORIZONTAL_LINE 
            };
                   
            // moves cursor to the give index of indicator and replaces the 
            // indicator with changed color to create animation effect
            switch (index)
            {
                case 0:
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);  //move at the beginning of the line
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION);
                    print(ANSI_CONSTANTS.BACKSPACE);     // delete that character
                    print(colors[this.colorIndex] + indicators[index]);
                    break;
                    
                case 1:
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);  //move at the beginning of the line
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION + 1);
                    print(ANSI_CONSTANTS.BACKSPACE);     // delete that character
                    print(colors[this.colorIndex] + indicators[index]);
                    break;
                    
                case 2:
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);  //move at the beginning of the line
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION + 2);
                    print(ANSI_CONSTANTS.BACKSPACE);     // delete that character
                    print(colors[this.colorIndex] + indicators[index]);
                    break;
                    
                case 3:
                    moveCursor(ANSI_CONSTANTS.CURSOR_DOWN, 1);
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);  //move at the beginning of the line
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION + 3);
                    print(ANSI_CONSTANTS.BACKSPACE);     // delete that character
                    print(colors[this.colorIndex] + indicators[index]);
                    break;    
                case 4:
                    moveCursor(ANSI_CONSTANTS.CURSOR_DOWN, 1);
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);  //move at the beginning of the line
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION + 2);
                    print(ANSI_CONSTANTS.BACKSPACE);     // delete that character
                    print(colors[this.colorIndex] + indicators[index]);
                    break;
                    
                case 5:
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);  //move at the beginning of the line
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION + 1);
                    print(ANSI_CONSTANTS.BACKSPACE);     // delete that character
                    print(colors[this.colorIndex] + indicators[index]);
                    break;
                case 6:
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);  //move at the beginning of the line
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION);
                    print(ANSI_CONSTANTS.BACKSPACE);     // delete that character
                    print(colors[this.colorIndex] + indicators[index]);
                    break;
                    
                case 7:
                    moveCursor(ANSI_CONSTANTS.CURSOR_UP, 1);    //move cursor up
                    moveCursor(ANSI_CONSTANTS.CURSOR_LEFT, LINE_BEGINNING);  //move at the beginning of the line
                    moveCursor(ANSI_CONSTANTS.CURSOR_RIGHT, DEFAULT_POSITION - 2);
                    print(ANSI_CONSTANTS.BACKSPACE);     // delete that character
                    print(colors[this.colorIndex] + indicators[index]);
                    moveCursor(ANSI_CONSTANTS.CURSOR_UP, 1);    //move cursor to the first line
                    break;
            }
        }
        
        //
        // build a new fading circular bar
        //
        private void buildFadingCircularBar()
        {
            for (int i = 0; i < this.MAX_STEPS; i++)
            {
                buildCircularBar(i, ANSI_CONSTANTS.BRIGHT_BLACK);
            }
        }
    }
}
