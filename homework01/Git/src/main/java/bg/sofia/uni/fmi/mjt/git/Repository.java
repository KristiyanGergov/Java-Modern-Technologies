package bg.sofia.uni.fmi.mjt.git;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Repository {
    private ArrayList<String> files;
    private int changedFiles;
    private String branch;
    private String commitHash;
    private Map<String, ArrayList<Commit>> repository;

    public Repository() {
        this.files = new ArrayList<>();
        this.changedFiles = 0;
        this.branch = "master";
        this.repository = new TreeMap<>();
        this.repository.put(branch, new ArrayList<>());
    }

    public String getBranch() {
        return branch;
    }

    public Result createBranch(String name) {
        Result result = new Result();
        if (repository.containsKey(name)) {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_CREATE_BRANCH, name));
            result.setSuccessful(false);
        } else {
            repository.put(name, getCurrentBranchCommits());
            result.setMessage(String.format(Constants.SUCCESSFUL_CREATE_BRANCH, name));
            result.setSuccessful(true);
        }
        return result;
    }

    public Result checkoutBranch(String name) {
        Result result = new Result();
        if (repository.containsKey(name)) {
            this.branch = name;
            result.setMessage(String.format(Constants.SUCCESSFUL_CHECKOUT_BRANCH, name));
            result.setSuccessful(true);
        } else {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_CHECKOUT_BRANCH, name));
            result.setSuccessful(false);
        }

        return result;
    }

    public Result checkoutCommit(String hash) {
        Result result = new Result();
        Commit commit = getCurrentBranchCommits()
                .stream()
                .filter(t -> t.getHash().equals(hash))
                .collect(toList()).get(0);
        if (commit == null) {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_CHECKOUT_COMMIT, hash));
            result.setSuccessful(false);
        } else {
            result.setMessage(String.format(Constants.SUCCESSFUL_CHECKOUT_COMMIT, hash));
            result.setSuccessful(true);
        }
        return result;
    }

    public Result add(String... files) {
        Result result = new Result();
        StringBuilder sb = new StringBuilder();
        boolean contained = false;

        for (String file :
                files) {
            sb.append(file).append(", ");
            if (this.files != null && this.files.indexOf(file) != -1) {
                sb.delete(0, sb.length());
                sb.append(file);
                contained = true;
                break;
            }
        }

        if (contained) {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_ADD, sb.toString()));
            result.setSuccessful(false);
        } else {
            result.setSuccessful(true);
            sb.delete(sb.length() - 2, sb.length());
            result.setMessage(String.format(Constants.SUCCESSFUL_ADD, sb.toString()));
            this.files.addAll(Arrays.asList(files));
            this.changedFiles += files.length;
        }

        return result;
    }

    public Result remove(String... files) {
        Result result = new Result();
        List<Integer> indexes = new LinkedList<>();
        boolean contained = true;
        for (String file :
                files) {
            int index = this.files.indexOf(file);
            if (index != -1) {
                indexes.add(index);
            } else {
                result.setMessage(String.format(Constants.UNSUCCESSFUL_REMOVE, file));
                result.setSuccessful(false);
                contained = false;
                break;
            }
        }
        if (contained) {
            StringBuilder sb = new StringBuilder();
            for (int index :
                    indexes) {
                sb.append(this.files.get(index)).append(", ");
                this.files.remove(index);
            }
            sb.delete(sb.length() - 2, sb.length());
            result.setMessage(String.format(Constants.SUCCESSFUL_REMOVE, sb.toString()));
            result.setSuccessful(true);
            this.changedFiles += files.length;
        }
        return result;
    }

    public Result commit(String message) {
        Result result = new Result();

        if (changedFiles > 0) {
            Commit commit = new Commit(message);
            this.commitHash = commit.getHash();
            getCurrentBranchCommits().add(commit);
            result.setSuccessful(true);
            result.setMessage(String.format(Constants.SUCCESSFUL_COMMIT, changedFiles));
            this.repository.put(branch, getCurrentBranchCommits());
            this.changedFiles = 0;
        } else {
            result.setMessage(Constants.UNSUCCESSFUL_COMMIT);
            result.setSuccessful(false);
        }
        return result;
    }

    public Result log() {
        Result result = new Result();

        if (getCurrentBranchCommits().size() == 0) {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_LOG, branch));
        } else {
            StringBuilder sb = new StringBuilder();
            for (Commit commit :
                    getCurrentBranchCommits()) {
                sb.append(String.format(Constants.SUCCESSFUL_LOG, commit.getHash(), commit.getDate(), commit.getMessage()));
            }
            result.setMessage(sb.toString());
        }

        return result;

    }

    public Commit getHead() {
        int size = repository.get(branch).size() - 1;
        return size < 0 ? null : repository.get(branch).get(size);
    }

    private ArrayList<Commit> getCurrentBranchCommits() {
        ArrayList<Commit> commits = new ArrayList<>();
        for (Commit commit :
                repository.get(branch)) {
            commits.add(commit);
            if (commit.getHash().equals(commitHash))
                break;
        }
        return commits;
    }
}