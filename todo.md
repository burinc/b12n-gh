## Use gh-utils instead of the cli to create the project

- https://cli.github.com/manual/gh_repo_create

```
# create a repository under your account using the current directory name
$ git init my-project
$ cd my-project
$ gh repo create

# create a repository with a specific name
$ gh repo create my-project

# create a repository in an organization
$ gh repo create cli/my-project

# disable issues and wiki
$ gh repo create --enable-issues=false --enable-wiki=false
```

Note: need to `brew install gh` and then create appropriate token to use

```
gh repo create \
  --enable-issues=false \
  --enable-wiki=false \
  --confirm \
  --description "https://github.com/.. (private forked)" \
  --homepage "https://github.com/.."
  --private
```

With this options:

```
  -y, --confirm               Skip the confirmation prompt
  -d, --description string    Description of the repository
      --enable-issues         Enable issues in the new repository (default true)
      --enable-wiki           Enable wiki in the new repository (default true)
  -g, --gitignore string      Specify a gitignore template for the repository
  -h, --homepage URL          Repository home page URL
      --internal              Make the new repository internal
  -l, --license string        Specify an Open Source License for the repository
      --private               Make the new repository private
      --public                Make the new repository public
  -t, --team name             The name of the organization team to be granted access
  -p, --template repository   Make the new repository based on a template repository
```
