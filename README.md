<img src="https://travis-ci.org/HenryFBP/2019-team-07f-mirror.svg?branch=master" alt="build:created">
<!-- TODO: Make this not the cloned test repo. -->

# 2019-team-07f
Private team repo for ITMT 430

# Monitoring

Visit http://localhost:{19999,19998,19997} for application metrics once the application is running.

These port numbers correspond to the `web`, `db`, and `storage` boxes respectively.

Additionally, dashboards are available in `/metrics/*.html`.

# Building

## Requirements

[Install Vagrant.](https://www.vagrantup.com/intro/getting-started/install.html)

[Install Packer.](https://packer.io/)

## The easy way

### Build the Packer image 

- Go into `/packer/` and follow the build directions for each box.

### Set up deploy keys

NOTE: This is not required if you edit the `Vagrantfile` and change the `USE_PUBLIC_REPO` flag to `true`.

- Create an RSA keypair and put them into `~/.ssh/` on your host machine. See
  [this
  guide](https://confluence.atlassian.com/bitbucketserver054/creating-ssh-keys-939508421.html)
  for a good overview of how to do it.
  
  The private key will be called `id_rsa_2019_team_07f` and the public key will be called
  `id_rsa_2019_team_07f.pub`.

  For example, my keys are stored at `C:\Users\henryfbp\.ssh\`.

- Add the PUBLIC key to this GitHub repository by clicking on `Settings > Deploy
  keys > Add deploy key` and copy-pasting the contents of `~/.ssh/id_rsa_2019_team_07f.pub`
  into the text box.

If your private key isn't in the place it's expected to be, then `vagrant up`
will error.

The key file (`id_rsa_2019_team_07f`) should look something like this:

    -----BEGIN RSA PRIVATE KEY-------
    SGFzIGFueW9uZSByZWFsbHkgYmVlbiBmYXIgYXMgZGVjaWRlZCB0byB1c2UgZXZl
    biBnbyB3YW50IHRvIGRvIGxvb2sgbW9yZSBsaWtlPyAKCllvdSd2ZSBnb3QgdG8g
    YmUga2lkZGluZyBtZS4gSSd2ZSBiZWVuIGZ1cnRoZXIgZXZlbiBtb3JlIGRlY2lk
    ZWQgdG8gdXNlIGV2ZW4gZ28gbmVlZCB0byBkbyBsb29rIG1vcmUgYXMgYW55b25l
    IGNhbi4gQ2FuIHlvdSByZWFsbHkgYmUgZmFyIGV2ZW4gYXMgZGVjaWRlZCBoYWxm
    IGFzIG11Y2ggdG8gdXNlIGdvIHdpc2ggZm9yIHRoYXQIE15IGd1ZXNzIGlzIHRoY
    XQgd2hlbiBvbmUgcmVhbGx5IGJlZW4gZmFyIGV2ZW4gYXMgZGVjaWRlZCBvbmNlI
    HRvIHVzZSBldmVuIGdvIHdhbnQsIGl0IGlzIHRoZW4gdGhhdCBoZSBoYXMgcmVhb
    Gx5IGJlZW4gZmFyIGV2ZW4gYXMgZGVjaWRlZCB0byB1c2UgZXZlbiBnbyB3YW50I
    HRvIGRvIGxvb2sgbW9yZSBsaWtlLiBJdCdzIGp1c3QgY29tbW9uIHNlbnNlLgo=
    -----END RSA PRIVATE KEY----- 
    
<!-- No, this is not a real private key. It is a copypasta of a Yahoo question. -->

NOTE: If you are using `puttygen.exe` on Windows, you [MUST convert the
key](https://help.cloudforge.com/hc/en-us/articles/215242303-Converting-PuTTY-private-keys-to-OpenSSH-format)
to OpenSSH format from `puttygen.exe`'s own format.

`puttygen.exe` uses its own format and is not compatible with the OpenSSH
format.
va
### Run the server

- Run `vagrant up` in this directory!

The web application will be accessible at `localhost:8080/searchable-video-library/`.

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
