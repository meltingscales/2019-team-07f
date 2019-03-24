By Henry Post

# Status update

Alright, here's the deal. I've done most of this project. It's been unfair to me
and has taken its toll on me mentally and I've reached a breaking point. I have
too much stress to continue.

I've decided that to make this fair, I've made a list of tasks that I want five
of finished. I'll give you all **two weeks** to accomplish these tasks, and if five
aren't finished by that time, I'm going to cut my losses and become a one-person
group.

All of these tasks are independent of each other and not terribly difficult to
accomplish. You are to use Google, and self-research if you do not know how to
do a task. I personally didn't know what Vagrant or Packer were before this
class, but was able to learn them, so you can learn this.

If you all can do any 5 of these tasks in two weeks, I will stay in this group.

I WILL check commits, so do not have one person do all of the tasks.

# Tasks

1.  NAME: Add CSS to the web server servlet (`/server/WebContent/resources/css/`) 
and ensure the CSS is clean-looking and simple. Don't use the OLD CSS, it was much too messy.

1.  NAME: Finish setting up an iSCSI target on the iSCSI box, and ensure it can be
mounted as a drive. Add a few test file transfers in the `Vagrantfile` that test
both the iSCSI target and initiator.

1.  NAME: Add 4 database entities that are related to the `Person` object. These are
.java files that Hibernate will use to create tables.
    
1.  NAME: Add a video upload form that inserts a video to a new `Video` table in the
MySQL database. It should also upload the file locally (no iSCSI needed)
    
1.  NAME: Generate an SSL certificate and add it to the Apache Tomcat server in the
Vagrant provisioning step. Verify HTTPS works.
    
1.  NAME: Convert a video to audio using a Java library. Add this as a unit test to
verify that this works. Make sure it works within the web server.

# Proof

## Graph of commits

![A graph of commits and LOC over time.](midterm-graph.PNG)

## Lines of code by author

This command:
```
git ls-tree -r HEAD | sed -Ee 's/^.{53}//' | \
while read filename; do file "$filename"; done | \
grep -E ': .*text' | sed -E -e 's/: .*//' | \
while read filename; do git blame --line-porcelain "$filename"; done | \
sed -n 's/^author //p' | \
sort | uniq -c | sort -rn
```

produces

```
   4641 Henry Post
   1411 HenryFBP
    558 unknown
    512 YiTing7092
    426 Idris Fagbemi
    374 npatel117
    325 jtron82
     26 Divin Gregis Baniekona
     16 Divin
      4 IDRIS FAGBEMI
      2 jhajek
henryfbp@henryfbp-VirtualBox:~/Github/2019-team-07f$ 
```
