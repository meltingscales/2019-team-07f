# 2019-team-07f
Private team repo for ITMT 430

# Building

## Requirements

[Install Vagrant.](https://www.vagrantup.com/intro/getting-started/install.html)

[Install Packer.](https://packer.io/)

## The easy way

### Build the Packer image 

- Go into `/packer/` and follow the build directions.

### Run the server

- Create an SSH deploy key and put it into `~/.ssh/id_rsa`.

  For example, mine is stored at `C:\Users\henryfbp\.ssh\id_rsa`.

  Then, add the PUBLIC key to this GitHub repository by clicking on
  `Settings > Deploy keys > Add deploy key` and copy-pasting the
  contents of ~/.ssh/id_rsa.pub` into the text box.
  
  If your private key isn't in the place it's expected to be, then
  `vagrant up` will error.

- Run `vagrant up` in this directory!

## The manual way
There are manual build instructions for the webserver inside `/server`.

The `/tools` subfolder has tools which are self-contained and have their own
build instructions.

# Communication

[Slack](https://itmt-430-group.slack.com)

[Trello](https://trello.com/b/03OdRjtq/2019-team-07f)

# Team members

## [Nihar Patel](https://github.com/npatel117)
npatel117@hawk.iit.edu

## [Henry Post](https://github.com/HenryFBP)
hpost@hawk.iit.edu

## [Idris Fagbemi](https://github.com/stwins60)
ifagbemi@hawk.iit.edu

## [Yi Ting Lin](https://github.com/YiTing7092)
ylin95@hawk.iit.edu
  
## [Divin Gregis Baniekona](https://github.com/anokeinab)
dbaniekona@hawk.iit.edu

## [Jimmy Tran](https://github.com/jtron82)
jtran8@hawk.iit.edu
