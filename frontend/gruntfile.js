module.exports = function (grunt) {

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-react');

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        clean: {
            dist: ['<%= pkg.paths.dist %>']
        },
        jshint: {
            dist: ['gruntfile.js', 'src/**/*.js', 'test/**/*.js']
        },
        less: {
            dist: {
                files: [
                    {
                        expand: true,
                        cwd: '<%= pkg.paths.src %>',
                        src: ['**/*.less'],
                        dest: '<%= pkg.paths.dist %>',
                        ext: '.css'
                    }
                ]
            }
        },
        copy: {
            dist: {
                files: [
                    {
                        expand: true,
                        cwd: '<%= pkg.paths.src %>',
                        src: ['**', '!**/*.jsx', '!**/*.less'],
                        dest: '<%= pkg.paths.dist %>'
                    },
                    {
                        expand: true,
                        cwd: '<%= pkg.paths.npm %>/bootstrap/dist',
                        src: ['css/bootstrap.css', 'fonts/**'],
                        dest: '<%= pkg.paths.dist %>'
                    },
                    {
                        expand: false,
                        src: '<%= pkg.paths.npm %>/requirejs/require.js',
                        dest: '<%= pkg.paths.dist %>/js/require.js'
                    },
                    {
                        expand: false,
                        src: '<%= pkg.paths.npm %>/jquery/dist/jquery.js',
                        dest: '<%= pkg.paths.dist %>/js/jquery.js'
                    },
                    {
                        expand: false,
                        src: '<%= pkg.paths.npm %>/react/dist/react-with-addons.js',
                        dest: '<%= pkg.paths.dist %>/js/react.js'
                    },
                    {
                        expand: false,
                        src: '<%= pkg.paths.npm %>/react-router/umd/ReactRouter.js',
                        dest: '<%= pkg.paths.dist %>/js/react-router.js'
                    }
                ]
            }
        },
        react: {
            dist: {
                files: [
                    {
                        expand: true,
                        cwd: '<%= pkg.paths.src %>',
                        src: ['**/*.jsx'],
                        dest: '<%= pkg.paths.dist %>',
                        ext: '.js'
                    }
                ]
            }
        },
        watch: {
            dist: {
                files: ['gruntfile.js', '<%= pkg.paths.src %>/**', '<%= pkg.paths.npm %>/**'],
                tasks: ['package:dist']
            }
        }
    });

    grunt.registerTask('package', ['jshint', 'copy', 'react', 'less']);
    grunt.registerTask('observe', ['package', 'watch']);
    grunt.registerTask('default', ['package:dist']);

};
