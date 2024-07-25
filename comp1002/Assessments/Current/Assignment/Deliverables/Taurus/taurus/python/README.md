# Taurus

Taurus is a Command-Line program written in Python which facilitates the analysis and creation of cryptocurrency trading data.

## Description

The program works by querying the [Binance RESTful API](https://binance-docs.github.io/apidocs/spot/en/) for financial information such as exchange information containing details about currently-trading trade pairs such as ETHBTC, 24-hour price change statistics and dated recent trade details including trade prices, quantities and quotes. Thereafter, this data is either output to the terminal or used to create additional data such as establishing all trade paths between two given assets. Moreover, users are able to save data retrieved from the API to JSON files and load the very same data into ```Taurus``` for later analysis.

## Getting Started

### Installation

1. Download the ```taurus-master``` archive by downloading [```taurus-master.zip```](https://github.com/tanakachitete/taurus/archive/master.zip)
2. Open a new terminal window in the directory where the archive was downloaded
3. Unzip the archive using the following command to obtain the ```taurus-master``` directory:
```
unzip taurus-master.zip
```
4. Delete ```taurus-master.zip``` using the following command:
```
rm taurus-master.zip
```
5. Change into the ```taurus-master/python``` directory by executing the following command:
```
cd taurus-master/python
```

### Execution
#### Interactive

* To execute ```Taurus.py``` in Interactive, execute the following:
```
python3 Taurus.py -i
```

#### Report

* To execute ```Taurus.py``` in Report, execute the following:
```
python Taurus.py -r <asset filename> <trade filename>
```

where ```<asset filename>``` and ```<trade filename>``` are both JSON files containing exchange information and trade details regarding a particular trade pair, respectively. 

```<asset filename>``` is obtained by running ```Taurus.py``` in Interactive. Upon program startup, at the ```Selection:``` prompt of ```Taurus (Interactive)```, enter ```4```. Then, at the ```Selection:``` prompt of ```Get and Display All Trade Pairs Involving User-specified Crypto```, enter ```2```. Afterwards, at the ```Selection:``` prompt of ```Get and Display All Trade Pairs Involving User-specified Crypto``` enter ```4```. Lastly, at the ```Filename (without .json extension):``` prompt enter a filename of your choosing (without the .json extension as the program will automatically append it to the filename you specify (e.g. ```AssetFile```). The file will then be saved in the current directory (e.g. ```AssetFile.json```). Exit the program by entering ```0``` at the ```Selection:``` prompt of ```Get and Display All Trade Pairs Involving User-specified Crypto``` and ```0``` at the ```Selection:``` prompt of ```Taurus (Interactive)```.

```<trade filename>``` is obtained by running ```Taurus.py``` in Interactive. Upon program startup, at the ```Selection:``` prompt of ```Taurus (Interactive)```, enter ```2```. Then, at the ```Selection:``` prompt of ```Get and Display Recent Trades (sorted by price, quantity and quote)```, enter ```2```. At the ```Base crypto-currency (case-insensitive):``` prompt enter a crypto-currency of your choosing (e.g. ```ETH```), then at the ```Quote crypto-currency (case-insensitive):``` prompt enter another crypto-currency of your choosing (e.g. ```BTC```) and, finally, at the ```Number of trades to view:``` prompt enter the number of recent trades you would like to view. Afterwards, at the ```Selection:``` prompt of ```Get and Display Recent Trades (sorted by price, quantity and quote)``` enter ```4```. Lastly, at the ```Filename (without .json extension):``` prompt enter a filename of your choosing (without the .json extension as the program will automatically append it to the filename you specify (e.g. ```TradeFile```). The file will then be saved in the current directory (e.g. ```TradeFile.json```). Exit the program by entering ```0``` at the ```Selection:``` prompt of ```Get and Display Recent Trades (sorted by price, quantity and quote)``` and ```0``` at the ```Selection:``` prompt of ```Taurus (Interactive)```.

### Restoration

* If you have saved ```.json``` files and wish to restore the ```taurus-master``` directory back to its original state, execute the following command:
```
rm *.json
```

## Author

Tanaka Chitete
* [Linkedin](https://www.linkedin.com/in/tanaka-chitete/)

## Acknowledgments

* Thank you to [DomPizzie](https://github.com/DomPizzie) for the [template](https://gist.github.com/DomPizzie/7a5ff55ffa9081f2de27c315f5018afc)
