const base = require('./build/webpack.base.config')
const dev = require('./build/webpack.dev.config')
const pro = require('./build/webpack.pro.config')
const param = process.env.npm_lifecycle_event

if (param === 'pro') {
    Object.assign(base,pro.config)
    base.plugins = [...base.plugins,...pro.plugins]
} else {
    Object.assign(base,dev)
}
module.exports = base