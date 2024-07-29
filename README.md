<!--
SPDX-FileCopyrightText: 2024 Imran Mustafa <imran@imranmustafa.net>

SPDX-License-Identifier: GPL-3.0-or-later
-->
# chatty
A lisp implementation of [ELIZA](https://github.com/codeanticode/eliza) for
all your digital therapy needs. 
## Installation
Download from <https://github.com/the0xdev/chatty> and use
[Leiningen](https://leiningen.org/) to compile the project.
``` shell
lein uberjar
```
Which will create a jar file in the `target` directory.
## Usage
After you've gotten the .jar file, you can treat it like any other. Just pass it
through java and chatty will run, no arguments necessary.
``` shell
java -jar chatty-0.1.0-standalone.jar
```
## Example

## Copyright/License
Copyright (C) 2024 Imran Mustafa

This program is free software: you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later
version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with
this program. If not, see https://www.gnu.org/licenses/. 
