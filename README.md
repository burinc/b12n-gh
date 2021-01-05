# b12n-gh

[![Clojars Project](https://img.shields.io/clojars/v/burinc/b12n-gh.svg)](https://clojars.org/burinc/b12n-gh)
[![Dependencies Status](https://jarkeeper.com/burinc/b12n-gh/status.svg)](https://jarkeeper.com/burinc/b12n-gh)
![ClojarsDownloads](https://img.shields.io/clojars/dt/burinc/b12n-gh)

Clojure library designed to automate some useful git command without the need to
use the web browser. Based on the [tentacles][] library with sensible default.
You can simply create new Github repo with one simple command and one config file.
TL;DR; `b12n-gh --config ~/Dropbox/config/github.edn --repo my-awesome-idea --push`

### Installation and usage as command line interface (CLI)

#### Pre-requisites

- [Clojure]()
- [Leiningen](http://leiningen.org/#install)

#### Installation

```sh
# Clone this repository locally
mkdir -p ~/projects

git clone https://github.com/burinc/b12n-gh.git ~/projects/b12n-gh

cd ~/projects/b12n-gh

# Create the `~/bin` folder to hold the executable
mkdir -p ~/bin

# Generate the standalone binary using `lein bin`
lein bin
```

Once this is done you should be able to just run the command to see basic usage:

```
Create new Github's project from a command line

Usage: b12n-gh [options]
  -c, --config CONFIG              ~/Dropbox/config/github.edn
  -r, --repo REPO
  -i, --init-commit
  -l, --remote-label REMOTE_LABEL  main
  -p, --push
  -h, --help

Options:

--config       CONFIG       full path to the config file e.g. ~/Dropbox/config/github.edn
--init-commit               run git-init and git commit on the local project
--repo         REPO         name of repository to be created
--remote-label REMOTE_LABEL remote label default to 'origin'
--push                      push the code to the remote repository as well

Examples:

a) To push existing project that have already contain some commit (e.g. skip -i)
b12n-gh -c ~/Dropbox/config/github.edn -r awesome-idea -l upstream -p

b) To create and push brand new project to Github and run initial commit
b12n-gh -c ~/Dropbox/config/github.edn -r awesome-idea -i -l upstream -p

c) To create brand new project to Github, run initial commit and skip pushing to remote branch.
b12n-gh -c ~/Dropbox/config/github.edn -r awesome-idea -i -l origin 
```

#### Usage

You will need to create the simple [edn](https://github.com/edn-format/edn) configuration
file. The file can have any name but the format must be something like:

I personally store this config file in the following location `~/Dropbox/config/github.edn`

```clj
{:username "YOUR-GITHUB-ID"
 :password "YOUR-GITHUB-PASSWORD"}
```

- To create a Github repository called `my-awesome-idea` only (no push is performed).

```sh
# long version
b12n-gh --config ~/Dropbox/config/github.edn --repo my-awesome-idea

# short version
b12n-gh -c ~/Dropbox/config/github.edn -r my-awesome-idea
```

- To create initial commit locally and then push the same codes to Github

```sh
# long version
b12n-gh --config ~/Dropbox/config/github.edn --repo my-awesome-idea --init-commit --push

# short version
b12n-gh -c ~/Dropbox/config/github.edn --repo my-awesome-idea -i -p
```

- To push local version of existing repository to new Github repository

```sh
# To create your repository in Github and push your changes to remote branch try
$b12n-gh --config ~/path/to/github-config.edn --repo my-awesome-id --push

# Or using short form
$b12n-gh -c ~/path/to/github-config.edn -r my-awesome-id -p

# Note without supplying -p the repository will be created remotely without initial git push
```

Now open `https//github.com/<YOUR-GITHUB-ID>/my-awesome-idea` in your browser.

### Development

- Leiningen

```clj
[net.b12n.github/b12n-gh "1.0.0"]
```

### Related Links

- Ways people are trying to address this issue on [Stack Overflow](http://stackoverflow.com/questions/2423777/is-it-possible-to-create-a-remote-repo-on-github-from-the-cli-without-opening-br)

### Roadmaps & TODOs

- [x] Add the intial commit with README.md file `touch README.md && git add README.md && git commit -m "Add README.md" && git push"`
- [x] Make use of [clj-jgit](https://github.com/clj-jgit/clj-jgit)
- [ ] Allow override of default options like ':public', ':description', etc

### License

Copyright © 2016-2021 Burin Choomnuan

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.

---

[tentacles]: http://github.com/Raynes/tentacles.git
[forked version]: https://github.com/agilecreativity/tentacles.git
