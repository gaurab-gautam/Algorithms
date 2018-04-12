
module.exports = class ConsoleProgressBar {
    constructor(barType = BarType.INDEFINITE) {
        this._barType = barType;
        this._step = 0;
        this._MAX_STEPS = 100;
        this._percentDone = 0;
        this._timer = null;
        this._task = null;
    }
    
    buildPercentProgressBar(bar)
    {
        let progressBar = "";
        
        if (bar !== "\u2588")           // not bar
        {
            progressBar += "[";
        }
        
        for (let i = 0; i <= this._percentDone; i += 10)
        {
            progressBar += bar;
        }
        
        progressBar = progressBar.padEnd(12 - this._percentDone);
        
        if (bar !== "\u2588")          // not bar
        {
            progressBar += "]";
            progressBar = progressBar.replace(" ", "*");
        }
        
        progressBar += "  [ " + this._percentDone + "% completed! ]";
        
        process.stdout.write("\r " + progressBar);
    }
    
    update(processed)
    {
        if (this._barType === BarType.STEP)
        {
            if ((processed % this._step) !== 0) return;

            this._percentDone += 10;
            this.buildPercentProgressBar("\u2588");       //[\u2588 = block]
        }
    }
    
    start()
    {
        if (this._barType === BarType.INDEFINITE)
        {
            process.stdout.write("Printing ");
            this._task = new ConsoleProgressBar.IndefiniteProgressTask();
            this._timer = setInterval(this._task.run, 1 * 1000); 
        }
        else {
            process.stdout.write("Printing:\n");
            let progressIcon = "\u2588";        //[\u2588 = block]
            this.buildPercentProgressBar(progressIcon);      
        }
    }
    
    stop()
    {
        if (this._barType === BarType.INDEFINITE)
        {
            clearTimeout(this._timer);
        }
    }
    
    // inner class
    static get IndefiniteProgressTask() {
        return class IndefiniteProgressTask
        {
            constructor() {
                this._numProgress = 5;
                this._RESET = "u001B[0m";
                this._GREEN = "\u001B[32m";
                this._bar = "\u275A";    //medium bar 
            }
            
            run() 
            {
                if (this._numProgress > 0) 
                {
                    process.stdout.write(green(this._bar));   
                    this._numProgress--;
                } 
                else 
                {
                    this._numProgress = 5;

                    process.stdout.write(reset("\rPrinting "));
                }
            }

            green(text)
            {
                return this._GREEN + text;
            }

            reset(text)
            {
                return this._RESET + text;
            }
        };
    }
};

const BarType = Object.freeze({
    STEP : "Step",
    INDEFINITE : "Indefinite"
});


