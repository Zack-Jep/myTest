# Front-end

To set up all the dependencies on the front-end run `yarn install`. This will
install all dependencies declared in the _package.json_ file into the
_node\_modules_ directory. All other `yarn` commands require that the
dependencies are downloaded.

## Tests

To run the tests simply execute the command `yarn test:dev`. This will open
Chrome and run all the tests. You can also run the tests like they run on Travis
CI with the command `yarn test:ci`. The difference is that it instead runs with
headless chrome.

## Code formatting

In order for everyone to format their code in the same way we use the tool
[prettier](https://prettier.io/). The configuration we're using is the
following:

```json
{
  "trailingComma": "es5",
  "singleQuote": true
}
```

### Formatting commands

- `yarn fmt:check`
  - Check if the all _.js_ files are styled according to the prettier
    configuration
- `yarn fmt:format`
  - Format all _.js_ files with prettier
