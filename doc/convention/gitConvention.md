# Git Convention
## Branch Structure

* main	: it is master branch, to keep the stable release version.
* qa	: It is a branch for integration/acceptable test.
* develop	:working branch.
* feature/001-create-prototype
* feature/002-design-map
* doc/019-create-design-doc

## branch naming convention
1. We should create new branch from the **develop branch**, using following naming convention when Starting a new feature,

[feature type]/[issue-no]-[simplified-issue-title]

e.g. to create a branch for a document, whose issue name is :025-read map into game, then its branch name will be 
feature/025-read-map

1. Commit and push your change to this newly created branch
1. Create Pull Request to merge the new branch to **develop** branch, make sure it is compilable.
1. Merge the new branch into **develop** branch after the Pull Request was reviewed and **approved**.

## Merge Order
Feature Branch --->> Develop   -- >> QA --->> Main (Release)
